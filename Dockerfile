FROM openjdk:15.0
COPY ./target/shelter-ua-1.0.0-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-jar","/app/shelter-ua-1.0.0-SNAPSHOT.jar"]
EXPOSE 8080