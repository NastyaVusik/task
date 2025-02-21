FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/hotel-1.0.0.jar /app/hotel.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "hotel.jar"]