# Use a Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom and source files
COPY pom.xml .
COPY src ./src

# Build the app using Maven
RUN mvn clean package -DskipTests

# ================================
# Stage 2: Runtime
# ================================
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
