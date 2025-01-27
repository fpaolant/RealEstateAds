# Step 1: Build
FROM maven:3.9.7-amazoncorretto-17 AS build

# Set the working directory
WORKDIR /app

# Copy file pom.xml and download dependencies
COPY search-service/pom.xml .
RUN mvn dependency:go-offline -B

# Copy the WSDL files into the build directory
COPY wsdl /app/../wsdl

# Copy the code and compile the project
COPY search-service/src /app/src

# Run code generation and Maven build command, skipping tests
RUN mvn clean generate-sources package -DskipTests

#CMD ["tail", "-f", "/dev/null"]

# Step 2: Run
FROM amazoncorretto:17.0.12-alpine3.19

# Set the working directory
WORKDIR /app

COPY  --from=build /wsdl /wsdl

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose ports
EXPOSE 7140

LABEL name="RealEstate Ads - Search Service" \
      description="Search Service for RealEstate Ads" \
      version="0.0.1"

# Command to run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]
