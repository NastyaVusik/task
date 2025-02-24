# Stage 1: Build the application
FROM maven:3-eclipse-temurin-21 AS build 
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/hotel-1.0.0.jar /app/hotel.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "hotel.jar"]