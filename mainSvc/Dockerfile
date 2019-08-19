#
# Build
#
#FROM maven:3.6.1-jdk-11-slim AS build

#WORKDIR /opt/mainSvc

#COPY pom.xml /opt/mainSvc/
#COPY src /opt/mainSvc/src/

#RUN mvn install

#
# Run
#
FROM maven:3.6.1-jdk-11-slim

EXPOSE 8080

WORKDIR /opt/mainSvc

# COPY --from=build /opt/mainSvc/target/mainSvc-1.0-jar-with-dependencies.jar mainSvc.jar
COPY ./target/mainSvc-1.0-jar-with-dependencies.jar mainSvc.jar

CMD [ "java", "-Xmn256m", "-Xmx768m", "-jar", "mainSvc.jar" ]