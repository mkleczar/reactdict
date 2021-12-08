# syntax=docker/dockerfile:1
FROM openjdk:15-alpine3.12

WORKDIR /app

COPY ./target/reactdict-0.0.1-SNAPSHOT.jar ./reactdict-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "reactdict-0.0.1-SNAPSHOT.jar"]