FROM java:8-alpine

ADD target/*.jar dubbo-provider.jar

ENTRYPOINT ["java","-jar","dubbo-provider.jar"]