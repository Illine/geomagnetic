#!/bin/bash

# Exporting required envs
# shellcheck disable=SC2155
export WEATHER_PROJECT_DIR="$(pwd)"
export ACTIVE_PROFILE=native
export TAG=$ACTIVE_PROFILE
export GEOMAGNETIC_OPTS="--add-opens java.base/java.time=ALL-UNNAMED --illegal-access=deny -Dspring.profiles.active=$ACTIVE_PROFILE"
export CONFIG_SERVICE_OPTS="-Dspring.profiles.active=$ACTIVE_PROFILE"

# Generation configs
ansible-playbook -i ansible/inventories/$ACTIVE_PROFILE ansible/playbook.yaml --vault-password-file=ansible/password/ansible-vault-$ACTIVE_PROFILE.password

# Building a project via './gradlew'
./gradlew build

# Starting the Docker containers
docker-compose -f docker-compose.yaml -f docker-compose-native.yaml up -d