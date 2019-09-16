#!/bin/bash

export GEOMAGNETIC_ACTIVE_PROFILE=dev
export GEOMAGNETIC_OPTS="--add-opens java.base/java.time=ALL-UNNAMED --illegal-access=deny -Dspring.profiles.active=$GEOMAGNETIC_ACTIVE_PROFILE"

export CONFIG_SERVICE_ACTIVE_PROFILE=native
export CONFIG_SERVICE_OPTS="-Dspring.profiles.active=$CONFIG_SERVICE_ACTIVE_PROFILE"