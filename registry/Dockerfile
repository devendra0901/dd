FROM java:8-jre

ADD target/discovery-registry-0.0.1-SNAPSHOT.jar registry.jar
ENTRYPOINT ["java", "-jar", "/registry.jar"]

EXPOSE 8761
