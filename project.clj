(defproject org.cyverse/service-logging "2.8.4-SNAPSHOT"
  :description "Common Logging Utilities for Clojure Projects"
  :url "https://github.com/cyverse-de/service-logging"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :plugins [[lein-ancient "0.7.0"]
            [jonase/eastwood "1.4.3"]
            [test2junit "1.4.4"]]
  :dependencies [[org.clojure/clojure "1.11.3"]
                 [org.clojure/tools.logging "1.3.0"]
                 [cheshire "5.13.0"]
                 [slingshot "0.12.2"]
                 [ch.qos.logback/logback-classic "1.5.6"]
                 [org.slf4j/jcl-over-slf4j "2.0.13"]
                 [net.logstash.logback/logstash-logback-encoder "7.4"]
                 [com.fasterxml.jackson.core/jackson-core "2.17.2"]
                 [com.fasterxml.jackson.core/jackson-databind "2.17.2"]
                 [com.fasterxml.jackson.core/jackson-annotations "2.17.2"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor "2.17.2"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-smile "2.17.2"]])
