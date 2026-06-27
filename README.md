# order-management-backend

## Overview

`order-management-backend` is a Spring Boot backend for managing products, customer orders, and admin order workflows. The project includes JWT-based authentication, role-secured customer and admin APIs, PostgreSQL persistence, and Spring Data JPA.

## Key Features

- Authentication and registration endpoints
- Customer-facing APIs for product browsing and order placement/cancellation
- Admin APIs for product CRUD and order status management
- Spring Security method-level access controls
- PostgreSQL persistence via Spring Data JPA
- JSON-based REST API with request validation

## Technology Stack

- Java 21 (LTS)
- Spring Boot 4.1.x
- Spring Web MVC
- Spring Data JPA
- Spring Security and OAuth2 resource server
- PostgreSQL JDBC driver
- Lombok for boilerplate reduction

## Getting Started

### Prerequisites

- Java 21 JDK installed
- Maven 3.x
- PostgreSQL database available

### Configuration

The default application configuration is in `src/main/resources/application.properties`.

Example database settings:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/om-db
spring.datasource.username=thet
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```

JWT settings are also configured in `application.properties`:

```properties
app.jwt.issuer=mpss-oms
app.jwt.secretKey=...
```

The application servlet path is prefixed with `/oms`.

### Build and Run

From the project root:

```bash
./mvnw clean package
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080/oms` by default.

## API Endpoints

### Authentication

- `POST /oms/auth/login` - Authenticate a user and return JWT auth result
- `POST /oms/auth/register` - Register a new user

### Customer APIs

- `GET /oms/customer/products` - List all products; optional `productName` query parameter
- `GET /oms/customer/products/{id}` - Get product details by ID
- `GET /oms/customer/orders` - List orders for the authenticated customer
- `GET /oms/customer/orders/{id}` - Get an individual order by ID (returned order must belong to authenticated user)
- `POST /oms/customer/orders/purchase` - Place a new order
- `PUT /oms/customer/orders/{id}/cancel` - Cancel an existing order

### Admin APIs

- `GET /oms/admin/products` - List all products
- `GET /oms/admin/products/{id}` - Get a product by ID
- `POST /oms/admin/products` - Create a new product
- `PUT /oms/admin/products/{id}` - Update an existing product
- `DELETE /oms/admin/products/{id}` - Delete a product by ID
- `GET /oms/admin/orders` - List all orders
- `GET /oms/admin/orders/{id}` - Get order details by ID
- `PUT /oms/admin/orders/status` - Update order status

## Project Structure

- `src/main/java/com/mpss/oms` - main application package
- `src/main/java/com/mpss/oms/auth` - authentication controllers and services
- `src/main/java/com/mpss/oms/controller/customer` - customer-facing REST controllers
- `src/main/java/com/mpss/oms/controller/admin` - admin REST controllers
- `src/main/java/com/mpss/oms/domain/entity` - JPA entity definitions
- `src/main/java/com/mpss/oms/domain/repo` - Spring Data repositories
- `src/main/java/com/mpss/oms/exception` - global exception handling and validation aspects
- `src/main/resources/application.properties` - runtime configuration

## Notes

- This project is configured for PostgreSQL runtime access.
- Ensure the database schema is available or allow Hibernate to create/update it via `spring.jpa.hibernate.ddl-auto=update`.
- For a production setup, replace the JWT secret and database credentials with secure values.
