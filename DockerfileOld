FROM maven:3.6.1-jdk-11 AS build
COPY . .
RUN mvn clean package -Pprod  -DskipTests
FROM openjdk:11-jdk-slim
COPY --from=build /target/docker-spring-boot.jar docker-spring-boot.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","docker-spring-boot.jar"]