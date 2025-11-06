# Enterprise Microservices Application

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A complete enterprise-ready, production-grade microservices architecture using **Spring Boot** backend and **React** frontend. This system is designed for local execution and containerization using Docker.

## 🏗️ Architecture Overview

This application consists of:

### Backend Microservices (Spring Boot)
1. **API Gateway** (Port 8080) - Routes requests and acts as reverse proxy
2. **Authentication Service** (Port 8081) - Handles JWT authentication
3. **User Service** (Port 8082) - Manages user information
4. **Product Service** (Port 8083) - Manages product catalog

### Frontend
- **React Application** (Port 3000) - Modern responsive UI

### Database
- **PostgreSQL** (Port 5432) - Relational database for all services

## 📁 Project Structure

```
enterprise-microservices-springboot-react/
├── api-gateway/
│   ├── src/main/java/com/example/apigateway
│   ├── src/main/resources/
│   ├── src/test/java/
│   ├── Dockerfile
│   └── pom.xml
├── auth-service/
│   ├── src/main/java/com/example/authservice
│   ├── src/main/resources/
│   ├── src/test/java/
│   ├── Dockerfile
│   └── pom.xml
├── user-service/
│   ├── src/main/java/com/example/userservice
│   ├── src/main/resources/
│   ├── src/test/java/
│   ├── Dockerfile
│   └── pom.xml
├── product-service/
│   ├── src/main/java/com/example/productservice
│   ├── src/main/resources/
│   ├── src/test/java/
│   ├── Dockerfile
│   └── pom.xml
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── App.js
│   ├── Dockerfile
│   └── package.json
├── docker-compose.yml
└── README.md
```

## ✨ Features

### Backend Features
- ✅ RESTful APIs following best practices
- ✅ JWT-based authentication
- ✅ Centralized exception handling
- ✅ Request validation
- ✅ JPA/Hibernate with PostgreSQL
- ✅ SLF4J logging
- ✅ Unit and integration tests (JUnit 5 + Mockito)
- ✅ Environment-based configuration
- ✅ Docker containerization

### Frontend Features
- ✅ React functional components
- ✅ React Router for navigation
- ✅ Axios for API calls
- ✅ Environment-based configuration
- ✅ Reusable component library
- ✅ Responsive design

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Node.js 18 or higher
- Maven 3.6+
- Docker & Docker Compose (for containerized deployment)
- PostgreSQL 14+ (if running locally without Docker)

### Local Development Setup (Without Docker)

#### 1. Setup PostgreSQL

Create databases for each service:
```sql
CREATE DATABASE authdb;
CREATE DATABASE userdb;
CREATE DATABASE productdb;
```

#### 2. Run Backend Services

Each Spring Boot service can be run independently:

**API Gateway:**
```bash
cd api-gateway
mvn clean install
mvn spring-boot:run
```

**Auth Service:**
```bash
cd auth-service
mvn clean install
mvn spring-boot:run
```

**User Service:**
```bash
cd user-service
mvn clean install
mvn spring-boot:run
```

**Product Service:**
```bash
cd product-service
mvn clean install
mvn spring-boot:run
```

#### 3. Run Frontend

```bash
cd frontend
npm install
npm start
```

The application will be available at:
- Frontend: http://localhost:3000
- API Gateway: http://localhost:8080

### 🐳 Docker Deployment

#### Build and Run with Docker Compose

```bash
# Build and start all services
docker-compose up --build

# Run in detached mode
docker-compose up -d

# Stop all services
docker-compose down

# View logs
docker-compose logs -f
```

The application will be available at:
- Frontend: http://localhost:3000
- API Gateway: http://localhost:8080
- PostgreSQL: localhost:5432

#### Build Individual Services

```bash
# Build specific service
docker build -t api-gateway ./api-gateway
docker build -t auth-service ./auth-service
docker build -t user-service ./user-service
docker build -t product-service ./product-service
docker build -t frontend ./frontend
```

## 🔌 API Endpoints

### Authentication Service

```
POST   /auth/register    - Register new user
POST   /auth/login       - Login and get JWT token
POST   /auth/refresh     - Refresh JWT token
```

### User Service

```
GET    /users           - Get all users
GET    /users/{id}      - Get user by ID
POST   /users           - Create new user
PUT    /users/{id}      - Update user
DELETE /users/{id}      - Delete user
```

### Product Service

```
GET    /products        - Get all products
GET    /products/{id}   - Get product by ID
POST   /products        - Create new product
PUT    /products/{id}   - Update product
DELETE /products/{id}   - Delete product
```

## 🧪 Testing

### Run Unit Tests

```bash
# Test all services
mvn clean test

# Test specific service
cd user-service
mvn test
```

### Run Integration Tests

```bash
mvn clean verify
```

## ⚙️ Configuration

### Environment Variables

Each service can be configured using environment variables:

**Database Configuration:**
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/dbname
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=password
```

**JWT Configuration (Auth Service):**
```
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000
```

**Frontend Configuration:**
```
REACT_APP_BACKEND_URL=http://localhost:8080
```

## 📚 Technology Stack

### Backend
- Spring Boot 3.x
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT (JSON Web Tokens)
- Lombok
- JUnit 5
- Mockito
- Maven

### Frontend
- React 18
- React Router DOM
- Axios
- CSS3
- Node.js
- npm

### DevOps
- Docker
- Docker Compose
- Git

## 🛡️ Security

- JWT-based authentication
- Password encryption using BCrypt
- CORS configuration
- SQL injection prevention via JPA
- Input validation on all endpoints

## 📝 Best Practices Implemented

- ✅ Separation of concerns
- ✅ Single Responsibility Principle
- ✅ Dependency Injection
- ✅ RESTful API design
- ✅ Exception handling
- ✅ Logging and monitoring
- ✅ Configuration management
- ✅ Containerization
- ✅ Code reusability
- ✅ Test-driven development

## 🔧 Troubleshooting

### Port Already in Use

```bash
# Check ports
netstat -ano | findstr :8080

# Kill process (Windows)
taskkill /PID <PID> /F

# Kill process (Linux/Mac)
kill -9 <PID>
```

### Database Connection Issues

- Verify PostgreSQL is running
- Check database credentials
- Ensure database exists
- Check firewall settings

### Docker Issues

```bash
# Remove all containers
docker-compose down -v

# Rebuild containers
docker-compose up --build --force-recreate

# Check container logs
docker logs <container-name>
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Your Name** - Initial work

## 🙏 Acknowledgments

- Spring Boot Documentation
- React Documentation
- Docker Documentation
- PostgreSQL Documentation

## 📧 Contact

For questions or support, please open an issue in the GitHub repository.

---

**Note:** This is a demonstration project for educational purposes. For production use, additional security hardening and performance optimization should be implemented.
