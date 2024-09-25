# Use a base image with JDK 17
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the Docker image
COPY target/*.jar app.jar

# Expose the port that Spring Boot uses
EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
