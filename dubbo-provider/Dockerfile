FROM openjdk:8-jdk-alpine

WORKDIR /srv

COPY ./target/*.jar /srv/dubbo-provider.jar

ENTRYPOINT ["java","-jar","/srv/dubbo-provider.jar"]