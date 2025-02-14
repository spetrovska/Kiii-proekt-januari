# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Copy the application JAR to the container
COPY target/your-app.jar app.jar

# Expose port 8080 (adjust if your app uses a different port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM openjdk:23-jdk-slim
COPY target/mongodb-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

