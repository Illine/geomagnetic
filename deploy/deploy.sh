#!/bin/bash

if [ "$ACTIVE_PROFILE" == "test" ]
then
  openssl aes-256-cbc -K $encrypted_52eb958a214b_key -iv $encrypted_52eb958a214b_iv -in deploy/ssh-keys/test_id_rsa.key.enc -out deploy/ssh-keys/test_id_rsa.key -d
  chmod 600 deploy/ssh-keys/test_id_rsa.key
  scp -o StrictHostKeyChecking=no -i deploy/ssh-keys/test_id_rsa.key docker-compose.yaml travis@84.201.154.238:/home/travis
  ssh -o StrictHostKeyChecking=no -i deploy/ssh-keys/test_id_rsa.key travis@84.201.154.238 'export CONFIG_SERVICE_OPTS="-Dspring.profiles.active=test"; export GEOMAGNETIC_OPTS="--add-opens java.base/java.time=ALL-UNNAMED --illegal-access=deny -Dspring.profiles.active=test"; export DOCKER_LASTEST_TAG="develop"; docker-compose -f docker-compose.yaml up -d'
else
  echo "not existed"
fi