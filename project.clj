(defproject ck.migrations "1.0.0"
  :description "Database Migrations module for Conskit"
  :url "https://github.com/conskit/ck.migrations"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [conskit "1.0.0-rc1"]]
  :profiles {:dev {:source-paths ["dev" "flyway"]
                   :resource-paths ["dev-resources"]
                   :dependencies [[puppetlabs/trapperkeeper "1.4.1" :classifier "test"]
                                  [puppetlabs/kitchensink "1.3.1" :classifier "test" :scope "test"]
                                  [org.flywaydb/flyway-core "4.0.3"]
                                  [org.clojure/java.jdbc "0.6.2-alpha1"]
                                  [com.h2database/h2 "1.4.192"]
                                  [midje "1.8.3"]]
                   :plugins [[lein-midje "3.2"]]}
             :flyway-db {:source-paths ["flyway"]
                         :dependencies [[org.flywaydb/flyway-core "4.0.3"]]}}
  :classifiers {:flyway :flyway-db})
