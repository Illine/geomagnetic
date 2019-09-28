#!/bin/bash

# Exporting required envs
export GEOMAGNETIC_ACTIVE_PROFILE=native
export GEOMAGNETIC_OPTS="--add-opens java.base/java.time=ALL-UNNAMED --illegal-access=deny -Dspring.profiles.active=$GEOMAGNETIC_ACTIVE_PROFILE"

export CONFIG_SERVICE_ACTIVE_PROFILE=native
export CONFIG_SERVICE_OPTS="-Dspring.profiles.active=$CONFIG_SERVICE_ACTIVE_PROFILE"


# Building a project via 'gradlew' on Linux
./gradlew build


# Starting the Docker containers
docker-compose -f docker-compose.yaml -f docker-compose-native.yaml up -d