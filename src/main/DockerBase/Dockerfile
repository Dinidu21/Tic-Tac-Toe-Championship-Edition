# Use a base image with JDK
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Package the application
RUN mvn clean package

# Set the command to run the application
CMD ["java", "-jar", "target/Tic-Tac-ToeChampionshipEdition-1.0-SNAPSHOT.jar"]