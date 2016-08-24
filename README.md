# ck.migrations [![Build Status](https://travis-ci.org/conskit/ck.migrations.svg?branch=master)](https://travis-ci.org/conskit/ck.migrations) [![Dependencies Status](https://jarkeeper.com/conskit/ck.migrations/status.svg)](https://jarkeeper.com/conskit/ck.migrations) [![Clojars Project](https://img.shields.io/clojars/v/ck.migrations.svg)](https://clojars.org/ck.migrations)

Database Migration module for [conskit](https://github.com/conskit/conskit)

## Installation
Add the dependency in the clojars badge above in your `project.clj`.

### FlywayDb support
Add the classifier "flyway" and the `org.flywaydb/flyway-core` library.

## Usage

Add the following to your `bootstrap.cfg`:

```
ck.migrations/migrations
```

Add the following to your `config.conf`

```properties
database: {
  classname:   "org.h2.Driver"
  subprotocol: "h2:file"
  subname:     "./demo"
  user:        "sa"
  password:    ""
  prefix: "" # the prefix to use for migration file names (default for flyway is 'V')
}
```

Add the dependency in your serivice and call the `migrate!` method in the init phase.

```clojure
(defservice
  my-service
  [[:CKMigration migrate!]]
  (init [this context]
    ...
    (migrate! :flyway :database)
  ...)
```

`migrate!` is called with a provider (`:flyway`) and the config key for where it should find the database settings (`:database`)

Add your migrations to the the code or, in the case of Flywaydb, add the sql needed for the migrations to your `resources/db/migration` folder.

Now when your service is initialized it will migrate the database.

### Alternatives
If Flywaydb is not your cup of tea you can always implement your own provider by providing a method that extends the `ck.migrations/migrate!*` multimethod

```clojure
(defmethod migrate!* :my-special-provider
  [{:keys [config]}]
  ;; logic))
  
;; Within service init
(migrate! :my-special-provider :database)
```

where `config` here is the configuration with the database and possibly other settings (basically anything under the key specified).

## License

Copyright © 2016 Your Name

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
