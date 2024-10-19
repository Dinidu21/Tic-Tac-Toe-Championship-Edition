# Build stage
FROM maven:3.9.6-eclipse-temurin-21 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package

# Run stage
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/Tic-Tac-ToeChampionshipEdition-1.0-SNAPSHOT.jar ./app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]
