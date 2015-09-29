(ns service-logging.middleware
  (:use [slingshot.slingshot :only [try+ throw+]] )
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log]
            [cheshire.core :as cheshire]
            [service-logging.thread-context :as tc]))

(defn flatten-map
  "Takes a nested map, and flattens it. The nested keys are combined with
  the given separator, and the keys can optionally be pre-prended with a string"
  [form separator pre]
  (into {}
        (mapcat (fn [[k v]]
                  (let [prefix (if pre (str pre separator (name k)) (name k))]
                    (if (map? v)
                      (flatten-map v separator prefix)
                      [[(keyword prefix) v]])))
                form)))

; TODO: Setup logger level conditional logging, which logs the body if the log
;       level is at it's highest
(defn- clean-request
  [request]
  (dissoc request
          :body
          :user-info
          :user-attributes
          :compojure.api.middleware/options
          :ring.swagger.middleware/data
          :route-middleware
          :route-handler))

(defn- clean-response
  [response]
  (dissoc response
          :body))

(defn log-request
  [{:keys [request-method uri] :as request}]
  (let [method (string/upper-case (name request-method))]
    (log/log 'AccessLogger :trace nil "entering service-logging.middleware/log-request")
    (log/log :debug "Unencoded request: " request)
    (tc/with-logging-context {:request (cheshire/encode (clean-request request))}
                             (log/log 'AccessLogger :info nil (str method " " uri)))))

(defn log-response
  ([level throwable {:keys [request-method uri]} response]
   (let [method (string/upper-case (name request-method))]
     (log/log 'AccessLogger :trace nil "entering service-logging.middleware/log-response")
     (tc/with-logging-context {:response (cheshire/encode (clean-response (assoc response
                                                                           :uri uri
                                                                           :request-method request-method)))}
                              (log/log 'AccessLogger level throwable (str method " " uri)))))
  ([request response]
    (log-response :info nil request response))
  ([level request response]
   (log-response level nil request response)))

(defn wrap-logging
  "Logs incoming requests and their responses with the 'AccessLogger' logger.

    If the response map contains a `throwable` key, the value will be given to
    the logger as an exception/throwable. Also, if the `throwable` key is not nil,
    it is assumed that there will also be an `exception` key in the response map.
    The `throwable` and `exception` keys are not logged nor passed with the response."
  [handler]
  (fn [request]
    (log-request request)
    (let [{:keys [throwable exception] :as response} (handler request)]
      (if (nil? throwable)
               (log-response request response)
               (tc/with-logging-context {:exception (cheshire/encode exception)}
                                        (log-response :error throwable request (dissoc response
                                                                                       :throwable
                                                                                       :exception))))
      (dissoc response
              :throwable
              :exception))))

; FIXME Replace usages of this function with compojure api validation exception handlers
(defn log-validation-errors
  [handler]
  (fn [request]
    (try+
      (handler request)
      (catch [:type :ring.swagger.schema/validation] {:keys [error]}
        (log/error (:throwable &throw-context) error)
        (throw+))
      (catch [:type :compojure.api.exception/request-validation] {:keys [error]}
        (log/error (:throwable &throw-context) error)
        (throw+))
      (catch [:type :compojure.api.exception/response-validation] {:keys [error]}
        (log/error (:throwable &throw-context) error)
        (throw+)))))
