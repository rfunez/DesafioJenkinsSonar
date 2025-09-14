FROM openjdk:17-jdk
WORKDIR /app
COPY target/Banking-1.0.0-SNAPSHOT.jar ./app.jar
ENTRYPOINT [ "java" , "-jar" , "app.jar"]