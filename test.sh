#!/bin/bash
# Remove installed version otherwise lein will use it when we run midje
rm ~/.lein/plugins/lein-noir-gen-*.jar
lein midje
