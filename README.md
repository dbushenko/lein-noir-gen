# lein-noir-gen
This is a CRUD-actions generator for a Noir-project. It is heavily inspired by the Rails generators.
Noir-gen creates default view and model for the specified entity. It uses the Mongodb to store the models.

The generated pages have some basic layout made with Bootstrap. The examples of generated pages are here: https://docs.google.com/open?id=0BzmL7xzGeOtOYjFkZjg4ZjQtYjMwNy00N2M4LTg2MzQtOGFhYzFhYmJkMWEy https://docs.google.com/open?id=0BzmL7xzGeOtOMmY4MDNlMjItODU5ZS00Y2Q1LWEzM2EtZGM1ZDRiNDUxMDk0

## Warning!

Version noir-gen 0.4.0 is adopted to the brand-new noir 1.3.0-beta2 which has breaking changes since it uses hiccup 1.0.

If you use noir 1.2.1 (and up to 1.3.0-beta1) then the right version of noir-gen is 0.3.0.

Otherwise, use noir-gen 0.4.0 and higher.

## Overview

The plugin comes with three commands:

$ lein noir-gen setup

will prepare such things like db.clj, server.clj, views/default.clj and some other resources. In most case you have to run it only once;

$ lein noir-gen model entity.subentity1 field1 field2 ... fieldN

and

$ lein noir-gen view entity.subentity1 field1 field2 ... fieldN

You may generate views and models all in one go with the command 'scaffold':

$ lein noir-gen scaffold entity.subentity1 field1 field2 ... fieldN

Also you need to add the congo-mongo dependency to your 'project.clj' like this: [congomongo "0.1.8"]. Each time you run setup, lein-noir-gen will tell you to add the dependency.

The options which you may set in your 'project.clj':
:noir-gen {:namespace alternate_namespace
           :database alternate_database}

If these options weren't set, lein-noir-gen will use the default namespace; the name of the database is also a default namespace.

## Usage
Install:
lein plugin install lein-noir-gen 0.4.0

Warning: you must have no previous versions of lein-noir-gen installed. You may delete it manually: $ rm ~/.lein/plugins/lein-noir-gen-*.jar

## Step by step (newproject)

$ lein noir new project-name

$ cd project-name

$ rm -f src/project-name/views/common.clj (optional)

$ rm -f src/project-name/views/welcome.clj (optional)

$ lein noir-gen setup

$ lein noir-gen scaffold article title author date body

$ lein noir-gen scaffold admin.pages title body

Add [congomongo "0.1.8"] to project.clj

Enjoy cruding!

Now test generated stuffs. Start mongod (in the other terminal, because you don't want to stop it, right?)

$ mongod --dbpath path-to-your-db

Then run:

$ lein run

Enjoy!

## Back-up

When a command is run, it will just write things as asked, without warning about old files.

## License

Copyright (C) 2012 Dmitry Bushenko, Hoang Minh Thang

Distributed under the Eclipse Public License, the same as Clojure.
