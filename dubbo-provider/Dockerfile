FROM openjdk:8-jre-slim

ADD target/*.jar dubbo-provider.jar

ENTRYPOINT ["java","-jar","dubbo-provider.jar"]