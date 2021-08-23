docker-up:
	docker-compose up -d

docker-stop:
	docker-compose stop

docker-down:
	docker-compose down

run:
	 sam local start-api --docker-network product_serveless_service_network

debug:
	sam local start-api --docker-network product_serveless_service_network --debug-port 5000

build:
	sam build
