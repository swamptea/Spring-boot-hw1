FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8081

COPY build/libs/SpringDemoApp-0.0.1-SNAPSHOT.jar myapp.jar

CMD ["java","-jar","myapp.jar"]