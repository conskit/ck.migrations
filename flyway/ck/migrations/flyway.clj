(ns ck.migrations.flyway
  (:require [clojure.tools.logging :as log]
            [ck.migrations :refer [migrate!*]])
  (:import (org.flywaydb.core Flyway)))

(defmethod migrate!* :flyway
  [{:keys [config]}]
  (log/info "Migrating Database using Flyway")
    (let [{:keys [subprotocol subname user password prefix] :or {prefix "V"}} config
          uri (format "jdbc:%s:%s" subprotocol subname)
          flyway (doto (Flyway.)
                   (.setDataSource uri user password nil)
                   (.setSqlMigrationPrefix prefix))]
      (.migrate flyway)))
