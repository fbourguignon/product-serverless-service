# product-serverless-service
Sample serverless application using Micronaut, AWS Lambda and API Gateway

- [x] [Micronaut 2.5.11](https://micronaut.io/)
- [x] [SAM](https://aws.amazon.com/pt/serverless/sam/) 
- [x] MongoDB
- [x] TestContainers
- [x] Java 11


### Start Application
This command start the docker containers, build the application and start sam local api.
```console
make run
```

### Debug Application

1 - configure intellij remote debugging.

![picture](img/intelijj-debug.png)

2 - run make debug
```console
make debug
```
3 - send any request to product controller

4 - start debug

### Endpoints

Create Product
```bash
curl -X POST \
  http://localhost:3000/products \
  -H 'content-type: application/json' \
  -d '{
    "name":"Playstation 5",
    "price":"4500.00",
    "description":"new sony console",
    "category":"console"
}'
```

Retrieve Product
```bash
curl -X GET \
  http://localhost:3000/products/{productId} \
  -H 'content-type: application/json' \
  -d '{
    "name":"Playstation 5",
    "price":"4500.00",
    "description":"new sony console",
    "category":"console"
}'
```

Update Product
```bash
curl -X PUT \
  http://localhost:3000/products/{productId} \
  -H 'content-type: application/json' \
  -d '{
  "price":"{productValue}"
}'
```

Delete Product
```bash
curl --request DELETE \
  --url http://localhost:3000/products/{productId} \
  --header 'content-type: application/json' 
```

List Products
```bash
curl --request GET \
  --url http://localhost:3000/products \
  --header 'content-type: application/json' 
```

