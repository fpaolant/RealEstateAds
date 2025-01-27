# Step 1: Build
FROM maven:3.9.7-amazoncorretto-17 AS build

# Set the working directory
WORKDIR /app

# Copy file pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the code and compile the project
COPY src ./src
RUN mvn package -DskipTests

# Step 2: Run
FROM amazoncorretto:17.0.12-alpine3.19

# Set the working directory
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose ports
EXPOSE 7100

LABEL name="RealEstate Ads - Gateway Service" \
      description="Gateway Service for RealEstate Ads" \
      version="0.0.1"

# Command to run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]