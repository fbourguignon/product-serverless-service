# product-serverless-service
Sample serverless application using Micronaut and AWS Lambda

- [x] [Micronaut 2.5.11](https://micronaut.io/)
- [x] [SAM](https://aws.amazon.com/pt/serverless/sam/) 
- [x] MongoDB
- [x] TestContainers


### Start Application

```console
make run
```

### Endpoints

Create Product
```console
POST /products HTTP/1.1
Host: localhost:3000
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 9b71b5ab-4160-c9fe-1aac-d573a19e36cb

{
    "name":"Playstation 5",
    "price":"4500.00",
    "description":"new generation console from sony",
    "category":"console"
}
```

Retrieve Product
```console
GET /products/{productId} HTTP/1.1
Host: localhost:3000
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 4846ef21-98f8-2c1b-f28b-403122bdb19f
```

Update Product
```console
PUT /products/{productId} HTTP/1.1
Host: localhost:3000
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: f30c5305-dec8-e988-2669-771ff00d6048

{
    "price":"4000.00"
}
```

Delete Product
```console
DELETE /products/{productId} HTTP/1.1
Host: localhost:3000
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 4aff8a8a-b618-7789-6833-5f744c0de578
```

List Products
```console
GET /products HTTP/1.1
Host: localhost:3000
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 247a12dc-8407-e5d5-253d-65273cd94eea
```

