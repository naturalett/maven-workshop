FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY . /app
RUN mvn clean package
RUN mv ./target/demo-0.0.1-SNAPSHOT.jar ./demo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","./demo-0.0.1-SNAPSHOT.jar"]