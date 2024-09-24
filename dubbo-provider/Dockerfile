#运行时镜像
FROM openjdk:8-jdk-alpine

# 设置应用工作目录
WORKDIR /app

# 将构建产物拷贝到运行时的工作目录中（从之前命名为 build 的构建阶段中复制构建产物到当前阶段的当前目录下）
COPY ./dubbo-provider/target/*.jar ./dubbo-provider.jar

# 服务暴露的端口
EXPOSE 20880

# 启动命令
ENTRYPOINT ["java","-jar","/app/dubbo-provider.jar"]