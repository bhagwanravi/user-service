# User Service

User Profile Management Service for Academy Management System

## Overview

The User Service is responsible for managing all user profiles in the academy management system including students, staff, and parents. It handles user creation, updates, authentication profiles, and batch assignments.

## Features

- **User Management**: Create, read, update, delete users
- **Role-based Access Control**: Support for ADMIN, STAFF, STUDENT, PARENT roles
- **Batch Management**: Assign users to batches and manage batch capacity
- **Search & Filtering**: Advanced search capabilities with role and status filters
- **Academy Multi-tenancy**: Support for multiple academies with data isolation
- **Service Discovery**: Integrated with Eureka for microservice discovery
- **Event-driven**: Kafka integration for asynchronous event handling

## API Endpoints

### User Management
- `POST /api/users` - Create new user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `PATCH /api/users/{id}/status` - Update user status

### User Search
- `GET /api/users/academy/{academyId}` - Get users by academy
- `GET /api/users/academy/{academyId}/role/{role}` - Get users by role
- `GET /api/users/search` - Search users
- `GET /api/users/search/role` - Search users by role

### Batch Management
- `POST /api/users/{userId}/batch/{batchId}` - Assign user to batch
- `DELETE /api/users/{userId}/batch/{batchId}` - Remove user from batch
- `GET /api/users/batch/{batchId}/students` - Get students in batch

## Database Schema

### Users Table
- Basic user information (name, email, phone)
- Role and status management
- Academy association for multi-tenancy
- Profile metadata (address, emergency contacts)

### Batches Table
- Batch information and capacity management
- Academy and course associations
- Status tracking

### User_Batches Table
- Many-to-many relationship between users and batches
- Batch capacity tracking

## Configuration

### Environment Variables
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password
- `SPRING_PROFILES_ACTIVE` - Active profile (dev/prod)

### Database
- PostgreSQL database on port 5433 (dev) or postgres-user:5432 (prod)
- Database name: `user_service_db`

### Service Discovery
- Eureka server: `http://localhost:8761/eureka/` (dev) or `http://eureka-server:8761/eureka/` (prod)

## Security

- JWT-based authentication (handled by API Gateway)
- Role-based authorization using Spring Security
- Method-level security with `@PreAuthorize`
- CORS configuration for cross-origin requests

## Monitoring

- Spring Boot Actuator endpoints
- Health checks: `/actuator/health`
- Metrics: `/actuator/metrics`
- Prometheus metrics: `/actuator/prometheus`

## Development

### Running Locally
```bash
mvn spring-boot:run
```

### Building
```bash
mvn clean package
```

### Docker
```bash
docker build -t user-service .
docker run -p 8084:8084 user-service
```

## Integration Points

### Auth Service
- User creation triggers authentication setup
- JWT token validation through API Gateway

### Academy Service
- Academy validation for user operations
- Course information for batch management

### Enrollment Service
- Student enrollment workflows
- Batch assignment coordination

### Notification Service
- User creation/update notifications
- Batch assignment notifications

## Error Handling

- Global exception handler with standardized error responses
- Validation error handling with field-level details
- Runtime exception handling with appropriate HTTP status codes

## Logging

- Structured logging with log levels
- File-based logging in production
- Console logging for development

## Testing

- Unit tests for service layer
- Integration tests for repositories
- API endpoint testing with MockMvc

## Deployment

### Docker Compose
The service is configured to run in Docker Compose with:
- PostgreSQL database container
- Eureka service discovery
- Kafka message broker

### Production Considerations
- Health checks and readiness probes
- Graceful shutdown handling
- Resource limits and monitoring
- Backup and recovery procedures
