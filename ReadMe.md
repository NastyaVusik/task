# Hotel Service

This service provides functionalities to manage hotels, including creating hotels, adding amenities, searching hotels, and generating statistics.

## Features

- **Create Hotel**: Add a new hotel to the repository.
- **Add Amenities**: Add amenities to an existing hotel.
- **Search Hotels**: Search for hotels based on various parameters.
- **Get Hotel Statistics**: Get the number of hotels grouped by specified parameters (brand, city, county, amenities).

## Endpoints

### Create Hotel

- **POST /hotels**
- Request Body: JSON representation of the hotel.

### Add Amenities

- **POST /hotels/{id}/amenities**
- Request Body: List of amenities to add.

### Search Hotels

- **GET /hotels/search**
- Query Parameters: `name`, `brand`, `city`, `county`, `amenities`

### Get Hotel Statistics

- **GET /histogram/{param}**
- Path Parameter: `param` (brand, city, county, amenities)

## Example

### Get Hotel Statistics by City

```
GET /histogram/city
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
GET /histogram/amenities
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
    Open your browser and navigate to `http://localhost:8089`.

## Swagger UI

To access the API documentation using Swagger UI, navigate to:
```
http://localhost:8089/swagger-ui/index.html
```

## Dependencies

- Spring Boot
- Spring Data JPA
- H2 Database (for testing)
- Lombok
- Springdoc OpenAPI

