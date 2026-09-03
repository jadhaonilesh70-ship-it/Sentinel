# Stage 1: Build the application using Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src
# Package the application (skip tests for faster builds in this demo)
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/target/sentinel-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
