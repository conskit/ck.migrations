(ns ck.migrations
  (:require
    [clojure.tools.logging :as log]
    [puppetlabs.trapperkeeper.core :refer [defservice]]
    [puppetlabs.trapperkeeper.services :refer [service-context]]))

(defmulti migrate!* :provider)

(defprotocol CKMigration
  "Migration Functions"
  (migrate! [this provider config-key]))

(defservice
  migrations CKMigration
  [[:ConfigService get-in-config]]
  (migrate! [this provider config-key]
            (migrate!* {:provider provider
                        :config (get-in-config [config-key])})))
