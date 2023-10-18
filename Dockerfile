#Start with a base image containing java runtime
FROM openjdk:11-slim as build

#Information about who maintains the code
MAINTAINER meghraj kurmi

#Add the application's jar to the container
COPY target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar springboot-blog-rest-api-0.0.1-SNAPSHOT.jar

#Execute the application
ENTRYPOINT ["java","-jar","/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar"]