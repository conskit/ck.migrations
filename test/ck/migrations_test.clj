(ns ck.migrations-test
  (:require
    [puppetlabs.trapperkeeper.app :as app]
    [puppetlabs.trapperkeeper.core :refer [defservice]]
    [puppetlabs.trapperkeeper.services :refer [service-context]]
    [puppetlabs.trapperkeeper.testutils.bootstrap :refer [with-app-with-cli-data]]
    [conskit.core :as ck]
    [conskit.macros :refer :all]
    [ck.migrations :refer [migrations]]
    [ck.migrations.flyway]
    [clojure.java.jdbc :as j])
  (:use midje.sweet))


(defcontroller
  my-controller
  []
  (action
    my-action
    [req]
    {:hello "world" :req req}))

(defprotocol ResultService
  (get-result [this]))

(defservice
  test-service ResultService
  [[:ActionRegistry register-controllers!]
   [:CKMigration migrate!]
   [:ConfigService get-in-config]]
  (init [this context]
        (register-controllers! [my-controller])
        (migrate! :flyway (get-in-config [:database]))
        context)
  (get-result [this]
              (j/query (get-in-config [:database])
                       ["select * from sample"])))

(with-app-with-cli-data
  app
  [ck/registry migrations test-service]
  {:config "./dev-resources/test-config.conf"}
  (let [serv (app/get-service app :ResultService)
        res (get-result serv)]
    (fact res =>
           [{:id 1, :name "Axel"} {:id 2, :name "Mr. Foo"} {:id 3, :name "Ms. Bar"}])))
