# lein-noir-gen
This is a CRUD-actions generator for a Noir-project. It is heavily inspired by the Rails generators.
Noir-gen creates default view and model for the specified entity. It uses the Mongodb to store the models and has support of heroku hosting out-of-the box.

The generated pages have some basic layout made with Bootstrap. The examples of generated pages are here: https://docs.google.com/open?id=0BzmL7xzGeOtOYjFkZjg4ZjQtYjMwNy00N2M4LTg2MzQtOGFhYzFhYmJkMWEy https://docs.google.com/open?id=0BzmL7xzGeOtOMmY4MDNlMjItODU5ZS00Y2Q1LWEzM2EtZGM1ZDRiNDUxMDk0

Also you can see a screencast about how to create a simple blog with Clojure, Noir and noir-gen in 15 minutes here: http://youtu.be/-Fo4djTlmVY

Version noir-gen 1.0.0 is adopted to the brand-new noir 1.3.0-beta3 which has breaking changes since it uses hiccup 1.0. Tested against Clojure 1.4.0.

##
Warning! This release doesn't work with leiningen 2.x.

## Overview

The plugin comes with the following commands:

1) Basic setup which should be done at least once:

$ lein noir-gen setup

This will prepare such things like db.clj, server.clj, views/default.clj and some other resources. In most cases you have to run it only once.

2) Model generating:

$ lein noir-gen model entity1 field1 field2 ... fieldN

or

$ lein noir-gen model entity.subentity1 field1 field2 ... fieldN

3) View generating:

$ lein noir-gen view entity1 field1 field2 ... fieldN

or

$ lein noir-gen view entity.subentity1 field1 field2 ... fieldN

3) You may generate views and models all in one go with the command 'scaffold':

$ lein noir-gen scaffold entity1 field1 field2 ... fieldN

or

$ lein noir-gen scaffold entity.subentity1 field1 field2 ... fieldN

4) Login functionality generating

$ lein noir-gen login

This will generate basic login/logout functionality. It is an experimental feature and the generated code is rather blunt. But you may use it as a quck and easy solution or as a stub for your future login functionality.

After generating login functionality you will be able to use three URIs:

* /login -- for login (sets the :username in the session);
* /logout -- for logout (resets the :username in the session);
* /user/edit -- for changing the password.

Bear in mind that when you create the application from scratch, there will be no users in the database that's why you will not be able to login. Use the '/user/edit' URI then. It will check whether the user 'root' exists. If no -- you will be able to set the password for the user 'root' without the login. Otherwise the login is required.

For database access you will need to add the congo-mongo dependency to your 'project.clj' like this: [congomongo "0.1.8"]. Each time you run setup, lein-noir-gen will tell you to add the dependency.

The options which you may set in your 'project.clj':
:noir-gen {:namespace alternate_namespace
           :database alternate_database}

If these options weren't set, lein-noir-gen will use the default namespace; the name of the database is also a default namespace.

## Usage
Install:
lein plugin install lein-noir-gen 0.5.0

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
