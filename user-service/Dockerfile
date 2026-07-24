FROM openjdk:17-ea-11-slim

VOLUME /tmp

COPY /build/libs/*-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]