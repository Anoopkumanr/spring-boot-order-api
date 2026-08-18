# Spring Boot Order API — Java 21 + MySQL

A complete CRUD/PATCH REST API for orders.

## Stack
- Java 21
- Spring Boot 3.5.4
- Spring Web
- Spring Data JPA / Hibernate
- MySQL
- Maven
- Jakarta Validation

## 1. Create the MySQL database

Run:

```sql
CREATE DATABASE order_db;
```

The application uses `spring.jpa.hibernate.ddl-auto=update`, so Hibernate will create/update the `orders` table automatically.

## 2. Configure MySQL

Open:

`src/main/resources/application.properties`

Change:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

Example:

```properties
spring.datasource.username=root
spring.datasource.password=root123
```

Do not commit real passwords to source control.

## 3. Run

From the project root:

```bash
mvn clean spring-boot:run
```

Or:

```bash
mvn clean package
java -jar target/order-api-0.0.1-SNAPSHOT.jar
```

Application:

`http://localhost:8080`

## 4. APIs

### Create order — POST

`POST /api/orders`

```json
{
  "customerName": "Anoop",
  "productName": "Laptop",
  "quantity": 2,
  "price": 75000.00,
  "status": "CREATED"
}
```

### Get all orders — GET

`GET /api/orders`

### Get one order — GET

`GET /api/orders/1`

### Update complete order — PUT

`PUT /api/orders/1`

```json
{
  "customerName": "Anoop Kumar",
  "productName": "MacBook Pro",
  "quantity": 1,
  "price": 150000.00,
  "status": "CONFIRMED"
}
```

PUT replaces the complete editable order representation.

### Partial update — PATCH

`PATCH /api/orders/1`

Only send fields that need changing:

```json
{
  "status": "SHIPPED"
}
```

Another example:

```json
{
  "quantity": 3,
  "price": 72000.00
}
```

### Delete — DELETE

`DELETE /api/orders/1`

Returns HTTP `204 No Content`.

## MySQL table

Hibernate creates a table similar to:

```sql
CREATE TABLE orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_name VARCHAR(120) NOT NULL,
    product_name VARCHAR(180) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id)
);
```

## Important design points

- `POST` inserts a new row.
- `PUT` updates all editable fields.
- `PATCH` updates only fields included in the request.
- `DELETE` removes the row.
- JPA/Hibernate handles SQL generation.
- `@Transactional` provides transaction boundaries.
- `@PrePersist` and `@PreUpdate` maintain timestamps.
- Validation prevents invalid quantity/price/input.
- Global exception handling returns clean 400/404 responses.

## Suggested curl tests

Create:

```bash
curl -X POST http://localhost:8080/api/orders   -H "Content-Type: application/json"   -d '{"customerName":"Anoop","productName":"Laptop","quantity":2,"price":75000.00,"status":"CREATED"}'
```

Patch:

```bash
curl -X PATCH http://localhost:8080/api/orders/1   -H "Content-Type: application/json"   -d '{"status":"SHIPPED"}'
```

Update:

```bash
curl -X PUT http://localhost:8080/api/orders/1   -H "Content-Type: application/json"   -d '{"customerName":"Anoop Kumar","productName":"Laptop","quantity":3,"price":70000.00,"status":"CONFIRMED"}'
```

Delete:

```bash
curl -X DELETE http://localhost:8080/api/orders/1
```
