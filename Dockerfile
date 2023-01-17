FROM maven:3.6.3-jdk-19 AS build
COPY . .
RUN mvn clean package
FROM openjdk:19-jdk-slim
ADD target/docker-spring-boot.jar docker-spring-boot.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","docker-spring-boot.jar"]