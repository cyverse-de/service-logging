(ns service-logging.thread-context
  (:import [org.slf4j MDC]))

(defn set-context!
  "set-context! puts the data from the context-map into the current thread's
  context."
  [context-map]
  (if (map? context-map)
    (doall (map (fn [[k# v#]] (MDC/put (name k#) (str v#))) context-map))))

(defn clear-context!
  "clear-context! clears out context map from the context. This should be
  used when the thread is about to be either shut down or returned to a thread
  pool."
  []
  (MDC/clear))

(defn set-ext-svc-tag!
  "Sets a value in the logging context which communicates the the operations in the current context
   are from the designated external service.

   After this key is set, it must be explicitly removed from the logging context when no longer
   needed. This can be accomplished with 'clear-ext-svc-tag!'."
  [tag]
  (MDC/put "ext_service" tag))

(defn clear-ext-svc-tag!
  "Clears the external service tag from the logging context."
  []
  (MDC/remove "ext_service"))

;; With thanks to https://github.com/vaughnd/clojure-example-logback-integration/
(defmacro with-logging-context
  "Use this to add a map to any logging wrapped in the macro. Macro can be nested.
  (with-logging-context {:key \"value\"} (log/info \"yay\"))
  "
  [context & body]
  `(let [wrapped-context# ~context
         ctx# (MDC/getCopyOfContextMap)]
     (try
       (set-context! wrapped-context#)
       ~@body
       (finally
         (if ctx#
           (MDC/setContextMap ctx#)
           (clear-context!))))))
