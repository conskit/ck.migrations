(ns ck.migrations
  (:require
    [clojure.tools.logging :as log]
    [puppetlabs.trapperkeeper.core :refer [defservice]]
    [puppetlabs.trapperkeeper.services :refer [service-context]]))

(defmulti migrate!* :provider)

(defprotocol CKMigration
  "Migration Functions"
  (migrate! [this provider config]))

(defservice
  migrations CKMigration
  []
  (migrate! [this provider config]
            (migrate!* {:provider provider
                        :config config})))
