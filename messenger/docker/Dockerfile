FROM openjdk:17
LABEL org.opencontainers.image.authors="BolshakovMA"
ADD /target/Chat-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]