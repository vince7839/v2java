FROM openjdk:8-jdk-alpine

COPY target/*.jar dubbo-provider.jar

ENTRYPOINT ["java","-jar","dubbo-provider.jar"]