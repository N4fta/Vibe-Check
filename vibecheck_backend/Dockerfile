FROM gradle:8.10.1-jdk17
WORKDIR /opt/app
COPY build/libs/vibecheck-backend.jar vibecheck-backend.jar
EXPOSE 8080

# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "vibecheck-backend.jar"]