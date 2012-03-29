# lein-noir-gen
This is a CRUD-actions generator for a Noir-project. It is heavily inspired by the Rails generators.
Noir-gen creates default view and model for the specified entity. It uses the Mongodb to store the models.

## Overview

The plugin comes with three commands:

$ lein noir-gen setup

will prepare such things like db.clj, server.clj, views/default.clj and some other resources. In most case you have to run it only once

$ lein noir-gen model entity.subentity1 field1 field2 ... fieldN

and

$ lein noir-gen view entity.subentity1 field1 field2 ... fieldN

will make model and view for entities. From this version, I break them apart to make it easier to customize cruding for your needs.

Also you need to add the congo-mongo dependency to your 'project.clj' like this: [congomongo "0.1.7"]. Each time you run setup, lein-noir-gen will tell you to add the dependency.

The options which you may set in your 'project.clj':
:noir-gen {:namespace alternate_namespace
           :database alternate_database}

If these options weren't set, lein-noir-gen will use the default namespace; the name of the database is also a default namespace.

## Usage
Install:
lein plugin install lein-noir-gen 0.4.0

Warning: you must have no previous versions of lein-noir-gen installed. You may delete it manually: $ rm ~/.lein/plugins/lein-noir-gen-*.jar

Run it from the root of your web-application.

lein noir-gen my-entity field1 field2 ... fieldN

$ lein noir-gen article title body author date published

If you want more complex directory structure, use dots "." in your entity string.

$ lein noir-gen entity.subentity1 field1 field2 ... fieldN

$ lein noir-gen entity.subentity2 field1 field2 ... fieldN

$ lein noir-gen another-entity.subentity field1 field2 ... fieldN

## Step by step (newproject)

$ lein noir new project-name

$ cd project-name

Add [congomongo "0.1.7"] to project.clj

Enjoy cruding!

Now test generated stuffs. Start mongod (in an other terminator, because you don't want to stop it, right?)

$ mongod --dbpath path-to-your-db

Then run:

$ lein run

Enjoy!

## Back-up

When a command is run, it will just write things as asked, without warning about old files.

## License

Copyright (C) 2012 Dmitry Bushenko, Hoang Minh Thang

Distributed under the Eclipse Public License, the same as Clojure.
