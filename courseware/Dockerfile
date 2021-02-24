#FROM java:11
FROM openjdk:11

VOLUME /tmp
EXPOSE 5500
COPY target/courseware-0.0.1-SNAPSHOT.jar app.jar
#CMD nohup sh -c "java -jar /app.jar --spring.profiles.active=prod"
ENTRYPOINT ["java","-jar","/app.jar"]
