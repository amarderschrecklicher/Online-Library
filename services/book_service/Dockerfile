# Start from official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy built jar from target/
COPY target/*.jar app.jar

# Command to run app
ENTRYPOINT ["java", "-jar", "app.jar"]
