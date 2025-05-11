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

# Créer un utilisateur non-root pour des raisons de sécurité
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Définir le répertoire de travail
WORKDIR /app

# Créer les répertoires pour les logs et donner les permissions à l'utilisateur spring
RUN mkdir -p /app/logs /app/target
RUN chown -R spring:spring /app

# Copier le JAR compilé depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Définir l'utilisateur non-root
USER spring:spring

# Exposer le port sur lequel l'application Spring Boot s'exécute
EXPOSE 8080

# Variable d'environnement pour les paramètres Java et la configuration de logging
ENV JAVA_OPTS=""
ENV LOGGING_FILE_PATH="/app/logs"
ENV LOGGING_CONFIG="classpath:logback-docker.xml"

# Commande pour exécuter l'application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dlogging.file.path=/app/logs -jar app.jar"]