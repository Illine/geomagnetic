export IMAGE_TAG=local

start:
	@echo "Gradle Build"
	@./gradlew assemble

	@echo "Docker Build"
	@eval $$(minikube docker-env) && docker image build -t illine/geomagnetic:${IMAGE_TAG} .

	@echo "Generating configs and Starting Service"
	@ansible-playbook -i .ansible/inventories/local --vault-password-file=.ansible/ansible_vault_local.txt -e env=local .ansible/main.yaml

stop:
	@echo "Removing and Stopping Service"
	@kubectl delete all -l app=geomagnetic
	@kubectl delete cm -l app=geomagnetic
	@kubectl delete secret -l app=geomagnetic
	@eval $$(minikube docker-env) && docker rmi illine/geomagnetic:${IMAGE_TAG}