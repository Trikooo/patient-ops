# Patient Service

A Spring Boot REST API microservice for patient management operations. This service provides CRUD operations for patient data with comprehensive validation, error handling, and database persistence.

## Features

- **Patient Management**: Create and retrieve patient records
- **Data Validation**: Email uniqueness validation and input validation
- **RESTful API**: Clean REST endpoints for patient operations
- **Database Support**: H2 in-memory database for development, PostgreSQL for production
- **Error Handling**: Global exception handling with proper HTTP status codes
- **Data Mapping**: DTO pattern for request/response mapping
- **Sample Data**: Pre-populated with test patient data

## Tech Stack

- **Java 25**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **H2 Database** (development)
- **PostgreSQL** (production)
- **Lombok** (reduced boilerplate)
- **Maven** (dependency management)
- **Bean Validation** (input validation)

## Project Structure

```bash
src/main/java/com/patientops/patient_service/
├── controller/          # REST controllers
│   ├── PatientController.java
│   └── PatientResponse.java
├── dto/                 # Data Transfer Objects
│   ├── PatientRequestDTO.java
│   └── PatientResponseDTO.java
├── exceptions/          # Exception handling
│   ├── EmailAlreadyExistsException.java
│   ├── GlobalExceptionHandler.java
│   └── PatientNotFoundException.java
├── mapper/              # Entity-DTO mapping
│   └── PatientMapper.java
├── model/               # JPA entities
│   └── Patient.java
├── repository/          # Data access layer
│   └── PatientRepository.java
├── service/             # Business logic
│   └── PatientService.java
└── PatientServiceApplication.java
```

## API Endpoints

### Get All Patients

```http
GET /patients
```

**Response:**

```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "name": "John Doe",
    "email": "john.doe@example.com",
    "address": "123 Main St, Springfield",
    "dateOfBirth": "1985-06-15"
  }
]
```

### Create Patient

```http
POST /patients
Content-Type: application/json
```

**Request Body:**

```json
{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "address": "456 Elm St, Shelbyville",
  "dateOfBirth": "1990-09-23"
}
```

**Response:**

```json
{
  "id": "generated-uuid",
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "address": "456 Elm St, Shelbyville",
  "dateOfBirth": "1990-09-23"
}
```

## Data Model

### Patient Entity

- **id**: UUID (auto-generated)
- **name**: String (required, max 100 characters)
- **email**: String (required, unique, valid email format)
- **address**: String (required)
- **dateOfBirth**: LocalDate (required)
- **registeredDate**: LocalDate (auto-set on creation)

## Getting Started

### Prerequisites

- Java 25 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd patient-service
   ```

2. **Build the project**

   ```bash
   mvn clean install
   ```

3. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

The application will start on port 4000.

### Database Access

#### H2 Console (Development)

- URL: <http://localhost:4000/h2-console>
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `admin_viewer`
- Password: `password`

## Configuration

### Application Properties

```properties
# Server Configuration
server.port=4000

# Database Configuration (H2)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=admin_viewer
spring.datasource.password=password

# H2 Console
spring.h2.console.path=/h2-console

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.sql.init.mode=always
```

## Sample Data

The application comes pre-populated with sample patient data including:

- John Doe (<john.doe@example.com>)
- Jane Smith (<jane.smith@example.com>)
- Alice Johnson (<alice.johnson@example.com>)
- And 12 additional test patients

## Error Handling

The service includes comprehensive error handling:

- **400 Bad Request**: Invalid input data or validation errors
- **404 Not Found**: Patient not found
- **409 Conflict**: Email already exists
- **500 Internal Server Error**: Unexpected server errors

### Example Error Response

```json
{
  "timestamp": "2024-01-15T10:30:00.000Z",
  "status": 409,
  "error": "Conflict",
  "message": "Email already exists: john.doe@example.com",
  "path": "/patients"
}
```

## Testing

### Run Tests

```bash
mvn test
```

### Manual Testing with cURL

**Get all patients:**

```bash
curl -X GET http://localhost:4000/patients
```

**Create a new patient:**

```bash
curl -X POST http://localhost:4000/patients \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Patient",
    "email": "test@example.com",
    "address": "123 Test St",
    "dateOfBirth": "1990-01-01"
  }'
```

## Development

### Adding New Features

1. Create DTOs in the `dto` package
2. Add service methods in `PatientService`
3. Create controller endpoints in `PatientController`
4. Add custom exceptions in the `exceptions` package
5. Update the global exception handler if needed

### Database Schema

The patient table is automatically created with the following structure:

```sql
CREATE TABLE patient (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    address VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    registered_date DATE NOT NULL
);
```

## Production Deployment

For production deployment, update the application properties to use PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/patientdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request
