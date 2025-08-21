(defproject org.cyverse/service-logging "2.8.5-SNAPSHOT"
  :description "Common Logging Utilities for Clojure Projects"
  :url "https://github.com/cyverse-de/service-logging"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :plugins [[lein-ancient "0.7.0"]
            [jonase/eastwood "1.4.3"]
            [test2junit "1.4.4"]]
  :dependencies [[org.clojure/clojure "1.12.1"]
                 [org.clojure/tools.logging "1.3.0"]
                 [cheshire "6.0.0"]
                 [slingshot "0.12.2"]
                 [ch.qos.logback/logback-classic "1.5.18"]
                 [org.slf4j/jcl-over-slf4j "2.0.17"]
                 [net.logstash.logback/logstash-logback-encoder "8.1"]])
