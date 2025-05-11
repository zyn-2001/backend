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

# Copier le JAR compilé depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Définir l'utilisateur non-root
USER spring:spring

# Exposer le port sur lequel l'application Spring Boot s'exécute
EXPOSE 8080

# Variable d'environnement pour les paramètres Java
ENV JAVA_OPTS=""

# Commande pour exécuter l'application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]