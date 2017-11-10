FROM java:8-jre

ADD target/config-server-0.0.1-SNAPSHOT.jar config.jar
ENTRYPOINT ["java", "-jar", "/config.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8890/health || exit 1

EXPOSE 8890
