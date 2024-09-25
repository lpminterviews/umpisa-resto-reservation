## Requirements
- Java 21
- Maven

### Building the code
```
./mvnw clean install
```

### Running the code
```
./mvnw spring-boot:run
```

### Sample queries

```
POST /reservation

curl --location 'http://localhost:8080/reservation' \
--header 'Content-Type: application/json' \
--data-raw '{
    "customerName": "Test Customer",
    "phoneNumber": "+63 999 1234567",
    "email": "customer1@test.com",
    "preferredChannels": ["EMAIL"],
    "numberOfGuests": 4,
    "reservationTime": "2024-09-25T20:01:00+08:00"
}'
```

```
GET /reservation/{id}

curl --location 'http://localhost:8080/reservation/51357460-96fa-43c7-964e-9fa59e252b1c'
```


```
PUT /reservation/{id}

curl --location --request PUT 'http://localhost:8080/reservation/51357460-96fa-43c7-964e-9fa59e252b1c' \
--header 'Content-Type: application/json' \
--data-raw '{
    "customerName": "Test Customer",
    "phoneNumber": "+63 999 1234567",
    "email": "customer1@test.com",
    "preferredChannels": ["SMS"],
    "numberOfGuests": 4,
    "reservationTime": "2024-09-30T13:30:00+08:00"
}'
```

```
GET /reservations?customerName=Test%20Customer

curl --location 'http://localhost:8080/reservations?customerName=Test%20Customer'
```

```
DELETE /reservation/{id}

curl --location --request DELETE 'http://localhost:8080/reservation/51357460-96fa-43c7-964e-9fa59e252b1c'
```


---

### OpenAPI descriptions:
http://localhost:8080/v3/api-docs

### Swagger:
http://localhost:8080/swagger-ui/index.html

### Postman collection
[./postman/UmpisaRestoReservation.postman_collection.json](./postman/UmpisaRestoReservation.postman_collection.json)

---

### TODO:

Input validations
- duplicate reservations
- date range
- number of guests
- ...

### Assumptions
- no session    
- no registration
- use customer name as key
- UTC timezone
- minimal validation (multiple/overlapping reservations)
- ...