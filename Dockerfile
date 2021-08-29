FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11

RUN mkdir /opt/results
ENV RESULT_LOCATION='/opt/results/output.txt'

RUN mkdir /app
WORKDIR /app

COPY --from=build /home/app/target/perfectstore.jar /app/perfectstore.jar

EXPOSE 8080
EXPOSE 9080
ENTRYPOINT ["java", "-jar", "/app/perfectstore.jar"]