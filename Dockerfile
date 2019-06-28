FROM maven:3.6.1-jdk-8-slim as build  
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:8-jre-alpine
COPY --from=build /usr/src/app/target/encurtador-url-reativo-1.0.0-SNAPSHOT.jar /usr/app/encurtador-url-reativo-1.0.0-SNAPSHOT.jar  
EXPOSE 8080  
ENTRYPOINT ["java","-jar", "-Dspring.config.name=application-docker", "/usr/app/encurtador-url-reativo-1.0.0-SNAPSHOT.jar"]