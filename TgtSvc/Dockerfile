#
# Build
#
FROM maven:3.6.1-jdk-11-slim AS build

# RUN apt-get update && apt-get -y install maven

WORKDIR /opt/tgtSvc

COPY pom.xml /opt/tgtSvc/
COPY src /opt/tgtSvc/src/

RUN mvn install

#
# Run
#
FROM maven:3.6.1-jdk-11-slim

EXPOSE 8080

WORKDIR /opt/tgtSvc

COPY --from=build /opt/tgtSvc/target/TgtSvc-1.0-jar-with-dependencies.jar TgtSvc.jar

CMD [ "java", "-Xmn256m", "-Xmx768m", "-jar", "TgtSvc.jar" ]