FROM java:8-jre

ADD target/UserManagement-0.0.1-SNAPSHOT.jar userservice.jar
ENTRYPOINT ["java", "-jar", "/userservice.jar"]

EXPOSE 8233
