FROM openjdk:11
RUN mkdir /app
WORKDIR /app
COPY target/perfectstore.jar /app/perfectstore.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/perfectstore.jar"]