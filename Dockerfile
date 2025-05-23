FROM eclipse-temurin:17-jdk-alpine as builder
ARG JAR_FILE=emsp-start/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080