FROM openjdk:17-oracle
WORKDIR /store
EXPOSE 8765
COPY target/grocery-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]