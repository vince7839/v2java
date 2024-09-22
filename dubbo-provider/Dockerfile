FROM openjdk:8-jdk-alpine

ADD target/*.jar dubbo-provider.jar

ENTRYPOINT ["java","-jar","dubbo-provider.jar"]