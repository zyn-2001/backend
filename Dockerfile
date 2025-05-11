FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN mkdir -p /app/logs /app/target && chmod 777 /app/logs /app/target
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -Dserver.port=$PORT -Dserver.address=0.0.0.0 -Dlogging.file.name=/app/logs/spring.log -jar app.jar"]
