#!/bin/sh
# This script is intended only for lein-noir-gen developers. 
# It automates building and installing the development version of the lein-noir-gen.

lein uberjar
rm ~/.lein/plugins/lein-noir-gen-*.jar
mv lein-noir-gen-0.4.2-standalone.jar ~/.lein/plugins/lein-noir-gen-0.4.2.jar
echo "Noir-gen successfully installed/upgraded!"
