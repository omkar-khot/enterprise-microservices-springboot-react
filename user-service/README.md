# User Service

User Service is a Spring Boot microservice for managing user profiles in the enterprise microservices architecture.

## Features

- ✅ Complete CRUD operations for user management
- ✅ RESTful API endpoints
- ✅ PostgreSQL database integration
- ✅ Docker containerization
- ✅ Comprehensive error handling
- ✅ Input validation
- ✅ CORS configuration for frontend integration
- ✅ Health check endpoints
- ✅ Unit and integration tests
- ✅ Swagger/OpenAPI documentation

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Database**: PostgreSQL 15
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito
- **Containerization**: Docker

## Prerequisites

- Java 17 or higher
- Maven 3.9+
- PostgreSQL 15+
- Docker (optional)

## Getting Started

### Local Development

1. **Clone the repository**
```bash
git clone https://github.com/omkar-khot/enterprise-microservices-springboot-react.git
cd enterprise-microservices-springboot-react/user-service
```

2. **Configure PostgreSQL**
```bash
# Create database
createdb userdb

# Update credentials in src/main/resources/application.yml if needed
```

3. **Build the application**
```bash
mvn clean package
```

4. **Run the application**
```bash
mvn spring-boot:run
```

The service will start on `http://localhost:8081`

### Docker Deployment

1. **Build Docker image**
```bash
docker build -t user-service:latest .
```

2. **Run with Docker Compose** (from project root)
```bash
docker-compose up user-service
```

## API Endpoints

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create new user |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/email/{email}` | Get user by email |
| GET | `/api/users` | Get all users |
| GET | `/api/users/page` | Get users (paginated) |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |
| PATCH | `/api/users/{id}/activate` | Activate user |
| PATCH | `/api/users/{id}/deactivate` | Deactivate user |
| GET | `/api/users/search/firstname?firstName={name}` | Search by first name |
| GET | `/api/users/search/lastname?lastName={name}` | Search by last name |
| GET | `/api/users/active` | Get active users |
| GET | `/api/users/role/{role}` | Get users by role |
| GET | `/api/users/count` | Count total users |
| GET | `/api/users/exists/email/{email}` | Check if email exists |
| GET | `/api/users/health` | Health check |

### Example Request

**Create User**
```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "1234567890",
    "role": "USER",
    "active": true
  }'
```

## API Documentation

Once the service is running, access Swagger UI at:
```
http://localhost:8081/swagger-ui.html
```

OpenAPI specification:
```
http://localhost:8081/api-docs
```

## Database Schema

```sql
CREATE TABLE user_profile (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    address VARCHAR(100),
    city VARCHAR(50),
    state VARCHAR(50),
    country VARCHAR(50),
    zip_code VARCHAR(10),
    role VARCHAR(20),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## Testing

**Run all tests**
```bash
mvn test
```

**Run specific test class**
```bash
mvn test -Dtest=UserServiceTest
```

**Test coverage**
- Unit tests for service layer
- Unit tests for controller layer
- Integration tests for application context

## Configuration

### Application Properties

Key configuration in `application.yml`:
- Database connection settings
- Server port (8081)
- JPA/Hibernate settings
- Logging levels

### Docker Configuration

Environment variables for Docker:
- `DB_USERNAME`: Database username (default: postgres)
- `DB_PASSWORD`: Database password (default: postgres)
- `SPRING_PROFILES_ACTIVE`: Active profile (default: docker)

## Health Monitoring

Health check endpoint:
```bash
curl http://localhost:8081/api/users/health
```

Management endpoints (available at `/actuator`):
- `/actuator/health`
- `/actuator/info`
- `/actuator/metrics`

## Error Handling

The service provides comprehensive error handling:
- **404 Not Found**: Resource doesn't exist
- **400 Bad Request**: Validation errors
- **500 Internal Server Error**: Server errors

Error response format:
```json
{
  "timestamp": "2025-11-06T21:45:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 123",
  "path": "/api/users/123"
}
```

## Project Structure

```
user-service/
├── src/
│   ├── main/
│   │   ├── java/com/example/userservice/
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── service/         # Business logic
│   │   │   ├── repository/      # Data access
│   │   │   ├── model/           # Domain entities
│   │   │   ├── dto/             # Data transfer objects
│   │   │   ├── exception/       # Exception handling
│   │   │   ├── config/          # Configuration
│   │   │   └── UserServiceApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application-docker.yml
│   └── test/
│       ├── java/com/example/userservice/
│       │   ├── controller/      # Controller tests
│       │   ├── service/         # Service tests
│       │   └── UserServiceApplicationTests.java
│       └── resources/
│           └── application-test.yml
├── Dockerfile
├── pom.xml
└── README.md
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.

## Support

For issues or questions, please create an issue in the GitHub repository.
