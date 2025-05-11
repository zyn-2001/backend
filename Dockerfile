FROM maven:3.8.5-openjdk-17-slim AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers pom.xml et src
COPY pom.xml .
COPY src ./src

# Compiler l'application en ignorant les tests
RUN mvn clean package -DskipTests

# Deuxième étape avec seulement le JRE pour un conteneur plus léger
FROM eclipse-temurin:17-jre-alpine

# Définir le répertoire de travail
WORKDIR /app

# Créer les répertoires pour les logs
RUN mkdir -p /app/logs /app/target && chmod 777 /app/logs /app/target

# Copier le JAR compilaé depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port sur lequel l'application Spring Boot s'exécute
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["sh", "-c", "java -Dserver.port=8080 -Dlogging.file.name=/app/logs/spring.log -jar app.jar"]