#!/bin/sh
# This script is intended only for lein-noir-gen developers. 
# It automates building and installing the development version of the lein-noir-gen.

lein uberjar
mv lein-noir-gen-0.3.0-standalone.jar ~/.lein/plugins/lein-noir-gen-0.3.0.jar
echo "Noir-gen successfully installed!"
