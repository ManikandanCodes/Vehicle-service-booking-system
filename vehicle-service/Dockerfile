
FROM openjdk:17-jdk-slim
# Set working directory inside container
WORKDIR /app
# Copy the JAR file from Jenkins build
COPY target/vehicle-service-0.0.1-SNAPSHOT.jar app.jar
# Expose port
EXPOSE 8080
# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
