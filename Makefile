export WEATHER_PROJECT_DIR=$(shell pwd)
export ACTIVE_PROFILE=native
export DOCKER_TAG=$(ACTIVE_PROFILE)
export GEOMAGNETIC_OPTS=--add-opens java.base/java.time=ALL-UNNAMED --illegal-access=deny -Dspring.profiles.active=$(ACTIVE_PROFILE)
export CONFIG_SERVICE_OPTS=-Dspring.profiles.active=$(ACTIVE_PROFILE)
export TAG=${ACTIVE_PROFILE}

local_start:
	@echo "Generation configs"
	@ansible-playbook -i ansible/inventories/${ACTIVE_PROFILE} ansible/playbook.yaml --vault-password-file=ansible/password/ansible-vault-${ACTIVE_PROFILE}.password

	@echo "Building a project via './gradlew'"
	@./gradlew build

	@echo "Starting the Docker containers"
	@docker-compose -f docker-compose.yaml -f docker-compose-native.yaml up -d --build

local_stop:
	@echo "Stoping and removing created docker containers"
	@docker-compose -f docker-compose.yaml -f docker-compose-native.yaml down -v --rmi local