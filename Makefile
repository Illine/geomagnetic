export IMAGE_TAG=local

start:
	@echo "Gradle Build"
	@./gradlew assemble

	@echo "Docker Build"
	@eval $(minikube docker-env) && docker build -t illine/geomagnetic:local .

	@echo "Generating configs and Starting Service"
	@ansible-playbook -i .ansible/inventories/local --vault-password-file=.ansible/ansible_vault_local.txt -e env=local .ansible/main.yaml

stop:
	@echo "Removing and Stopping Service"
	@kubectl delete all -l app=geomagnetic
	@kubectl delete cm -l app=geomagnetic
	@kubectl delete secret -l app=geomagnetic