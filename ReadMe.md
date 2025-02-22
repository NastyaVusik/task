# Hotel Service

This service provides functionalities to manage hotels, including creating hotels, adding amenities, searching hotels, and generating statistics.

## Features

- **Create Hotel**: Add a new hotel to the repository.
- **Add Amenities**: Add amenities to an existing hotel.
- **Search Hotels**: Search for hotels based on various parameters.
- **Get Hotel Statistics**: Get the number of hotels grouped by specified parameters (brand, city, county, amenities).

## Endpoints

### Create Hotel

- **POST /property-view/hotels**
- Request Body: JSON representation of the hotel.

### Add Amenities

- **POST /property-view/hotels/{id}/amenities**
- Request Body: List of amenities to add.

### Search Hotels

- **GET /property-view/hotels/search**
- Query Parameters: `name`, `brand`, `city`, `county`, `amenities`

### Get Hotel Statistics

- **GET /property-view/histogram/{param}**
- Path Parameter: `param` (brand, city, county, amenities)

## Example

### Get Hotel Statistics by City

```
GET /property-view/histogram/city
```

Response:
```json
{
    "Minsk": 1,
    "Moscow": 2,
    "Mogilev": 0
}
```

### Get Hotel Statistics by Amenities

```
GET /property-view/histogram/amenities
```

Response:
```json
{
    "Free parking": 1,
    "Free WiFi": 20,
    "Non-smoking rooms": 5,
    "Fitness center": 0
}
```

## How to Run

1. **Build the project**:
    ```sh
    mvn clean install
    ```

2. **Run the application**:
    ```sh
    mvn spring-boot:run
    ```

3. **Access the service**:
    Open your browser and navigate to `http://localhost:8089/property-view`.

## Building and Running with Docker

To build and run the application using Docker, follow these steps:

1. **Build the Docker image:**

   ```sh
   docker build -t hotel-app .
   ```

2. **Run the Docker container:**

   ```sh
   docker run -p 8092:8092 hotel-app
   ```

This Dockerfile uses multi-stage building to optimize the final image size. The application is first built using a Maven image, and then the resulting JAR file is copied into a slim OpenJDK image.

## Swagger UI

To access the API documentation using Swagger UI, navigate to:
```
http://localhost:8092/property-view/swagger-ui/index.html
```

## How to Run Tests

To run the tests, use the following Maven command:
```sh
mvn test
```

## Dependencies

- Spring Boot
- Spring Data JPA
- H2 Database (for testing)
- Lombok
- Springdoc OpenAPI
- Liquibase
- MapStruct

