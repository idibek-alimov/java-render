FROM openjdk:17-jdk-alpine3.14
EXPOSE 8080
ADD  /target/docker-spring-boot.jar docker-spring-boot.jar
ENTRYPOINT ["java","-jar","docker-spring-boot.jar"]
