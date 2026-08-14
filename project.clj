(defproject org.cyverse/service-logging "2.8.6-SNAPSHOT"
  :description "Common Logging Utilities for Clojure Projects"
  :url "https://github.com/cyverse-de/service-logging"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :plugins [[lein-ancient "1.0.0"]
            [jonase/eastwood "1.4.3"]
            [test2junit "1.4.4"]]
  ;; Fail the build on a new dependency conflict rather than printing a
  ;; warning nobody reads.
  :pedantic? :abort
  ;; The jackson-* artifacts must move together: mixing minors across
  ;; core/databind/annotations surfaces as NoSuchMethodError at runtime rather
  ;; than as a resolution failure, and :pedantic? cannot catch it because each
  ;; artifact is individually unambiguous. cheshire 6.2.0 brings core/cbor/smile
  ;; at 2.21.1 while logstash-logback-encoder brings databind 2.18.3, so databind
  ;; and annotations are aligned to 2.21 to match.
  :managed-dependencies [[com.fasterxml.jackson.core/jackson-annotations "2.21"]
                         [com.fasterxml.jackson.core/jackson-databind "2.21.1"]]
  :dependencies [[org.clojure/clojure "1.12.5"]
                 [org.clojure/tools.logging "1.3.1"]
                 [cheshire "6.2.0"]
                 [slingshot "0.12.2"]
                 [ch.qos.logback/logback-classic "1.5.18"]
                 [org.slf4j/jcl-over-slf4j "2.0.17"]
                 [net.logstash.logback/logstash-logback-encoder "8.1"]])
