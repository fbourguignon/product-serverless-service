docker-up:
	docker-compose up -d

docker-stop:
	docker-compose stop

docker-down:
	docker-compose down

run:
	@make docker-up
	 sam build
	 sam local start-api --docker-network product_serveless_service_network --warm-containers EAGER

debug:
	@make docker-up
	 sam build
	 sam local start-api --docker-network product_serveless_service_network --warm-containers EAGER --debug-port 5000


