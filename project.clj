(defproject org.cyverse/service-logging "2.8.4-SNAPSHOT"
  :description "Common Logging Utilities for Clojure Projects"
  :url "https://github.com/cyverse-de/service-logging"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :plugins [[test2junit "1.2.2"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]       ; exported
                 [cheshire "5.10.0"]
                 [slingshot "0.12.2"]
                 [ch.qos.logback/logback-classic "1.2.5"]
                 [org.slf4j/jcl-over-slf4j "1.7.32"]
                 [net.logstash.logback/logstash-logback-encoder "6.6"]
                 [com.fasterxml.jackson.core/jackson-core "2.10.2"]
                 [com.fasterxml.jackson.core/jackson-databind "2.10.2"]
                 [com.fasterxml.jackson.core/jackson-annotations "2.10.2"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor "2.10.2"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-smile "2.10.2"]])
