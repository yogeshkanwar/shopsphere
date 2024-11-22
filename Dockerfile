# Use a base image with JDK
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/Testing-0.0.1-SNAPSHOT.jar /app/Testing-0.0.1-SNAPSHOT.jar


# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "Testing-0.0.1-SNAPSHOT.jar"]

