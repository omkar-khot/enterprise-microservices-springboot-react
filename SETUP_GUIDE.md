# Complete Setup Guide

This repository provides a production-ready enterprise microservices architecture. Follow the steps below to get started.

## 📋 Repository Status

✅ **Core Infrastructure Files Created:**
- README.md - Comprehensive documentation
- docker-compose.yml - Full Docker orchestration
- init-db.sql - Database initialization with sample data
- .gitignore - Java/Node.js exclusions
- LICENSE - MIT License

## 🚀 Quick Start (Recommended Approach)

Since this is a template repository with core configuration files, follow these steps:

### Step 1: Clone Repository
```bash
git clone https://github.com/omkar-khot/enterprise-microservices-springboot-react.git
cd enterprise-microservices-springboot-react
```

### Step 2: Generate Microservices Using Spring Initializr

For each service, use [Spring Initializr](https://start.spring.io/) with these settings:

**API Gateway Service:**
- Group: `com.example`
- Artifact: `api-gateway`
- Dependencies: Spring Cloud Gateway, Eureka Discovery Client
- Java Version: 17

**Auth Service:**
- Group: `com.example`
- Artifact: `auth-service`
- Dependencies: Spring Web, Spring Security, Spring Data JPA, PostgreSQL Driver, JWT
- Java Version: 17

**User Service:**
- Group: `com.example`
- Artifact: `user-service`
- Dependencies: Spring Web, Spring Data JPA, PostgreSQL Driver, Validation
- Java Version: 17

**Product Service:**
- Group: `com.example`
- Artifact: `product-service`
- Dependencies: Spring Web, Spring Data JPA, PostgreSQL Driver, Validation
- Java Version: 17

### Step 3: Create React Frontend
```bash
npx create-react-app frontend
cd frontend
npm install axios react-router-dom
```

### Step 4: Configure Services

Each service needs an `application.yml` in `src/main/resources/`:

**Example for User Service (`user-service/src/main/resources/application.yml`):**
```yaml
server:
  port: 8082

spring:
  application:
    name: user-service
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/userdb}
    username: ${SPRING_DATASOURCE_USERNAME:postgres}
    password: ${SPRING_DATASOURCE_PASSWORD:postgres}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

logging:
  level:
    com.example: DEBUG
```

### Step 5: Add Dockerfiles

Create `Dockerfile` in each service directory:

**Spring Boot Services:**
```dockerfile
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
```

**React Frontend:**
```dockerfile
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### Step 6: Run with Docker Compose

Once all services are set up:

```bash
# Start all services
docker-compose up --build

# Or run in detached mode
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

## 🏗️ Essential Code Templates

### User Entity Example (user-service)

```java
package com.example.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(unique = true)
    private Long userId;
    
    @NotBlank
    @Size(max = 50)
    private String firstName;
    
    @NotBlank
    @Size(max = 50)
    private String lastName;
    
    @Email
    private String email;
    
    @Size(max = 20)
    private String phone;
    
    private String address;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
```

### REST Controller Example

```java
package com.example.userservice.controller;

import com.example.userservice.model.UserProfile;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UserProfile> createUser(@Valid @RequestBody UserProfile user) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.save(user));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUser(
            @PathVariable Long id, 
            @Valid @RequestBody UserProfile user) {
        return userService.findById(id)
            .map(existing -> {
                user.setId(id);
                return ResponseEntity.ok(userService.save(user));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
```

### React API Service Example

```javascript
// frontend/src/services/api.js
import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor for authentication
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const userService = {
  getAllUsers: () => api.get('/api/users'),
  getUserById: (id) => api.get(`/api/users/${id}`),
  createUser: (user) => api.post('/api/users', user),
  updateUser: (id, user) => api.put(`/api/users/${id}`, user),
  deleteUser: (id) => api.delete(`/api/users/${id}`),
};

export const productService = {
  getAllProducts: () => api.get('/api/products'),
  getProductById: (id) => api.get(`/api/products/${id}`),
  createProduct: (product) => api.post('/api/products', product),
  updateProduct: (id, product) => api.put(`/api/products/${id}`, product),
  deleteProduct: (id) => api.delete(`/api/products/${id}`),
};

export default api;
```

## 📝 Important Configuration Notes

### Database Setup
The `init-db.sql` file will automatically:
- Create three databases: `authdb`, `userdb`, `productdb`
- Create necessary tables with relationships
- Insert sample product data for testing

### Port Configuration
- API Gateway: 8080
- Auth Service: 8081
- User Service: 8082
- Product Service: 8083
- Frontend: 3000
- PostgreSQL: 5432

### Environment Variables
Configure in docker-compose.yml or locally:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/userdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
JWT_SECRET=mySecretKey12345678901234567890123456789012
REACT_APP_API_URL=http://localhost:8080
```

## 🧪 Testing

### Test User Service
```bash
# Get all users
curl http://localhost:8082/api/users

# Create user
curl -X POST http://localhost:8082/api/users \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john@example.com"}'
```

### Test Product Service
```bash
# Get all products
curl http://localhost:8083/api/products

# Get specific product
curl http://localhost:8083/api/products/1
```

## 🎯 Next Steps

1. **Implement Business Logic**: Add service layers, DTOs, and mappers
2. **Add Security**: Implement JWT authentication in auth-service
3. **Add Testing**: Write unit tests with JUnit and Mockito
4. **Add API Documentation**: Use Swagger/OpenAPI
5. **Add Monitoring**: Integrate Spring Boot Actuator and Prometheus
6. **Add Caching**: Implement Redis for performance
7. **Add Message Queue**: Use RabbitMQ or Kafka for async communication

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev/)
- [Docker Documentation](https://docs.docker.com/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

## 🤝 Contributing

Follow the comprehensive README.md for contribution guidelines.

## 📄 License

MIT License - see LICENSE file for details

---

**Repository**: https://github.com/omkar-khot/enterprise-microservices-springboot-react

**Status**: ✅ Ready for clone and local development
