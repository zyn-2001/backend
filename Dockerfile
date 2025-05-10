# Build stage
FROM maven:3.8-openjdk-17-slim as build

# Set character encoding for Maven
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

WORKDIR /app

# Copy pom.xml first for dependency resolution
COPY pom.xml .
# Copy only the necessary resources for the build
COPY src ./src
COPY zyn ./zyn

# Build the application with proper encoding
RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8

# Runtime stage
FROM openjdk:17-slim

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar


# Environment variables (Railway will inject DATABASE_URL, PORT, etc.)
ENV SPRING_PROFILES_ACTIVE=dev
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

EXPOSE 8080

# Command to run the application
CMD ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
