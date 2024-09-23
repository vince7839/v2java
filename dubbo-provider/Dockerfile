#使用基础镜像maven:3.6.0-jdk-8-slim来构建一个Maven项目（AS build 给这个构建阶段命名为 build，在后面引用这个阶段的镜像时直接使用别名就可以）
FROM registry.cn-hangzhou.aliyuncs.com/maven:3.6.0-jdk-8-slim AS build

#设置应用工作目录
WORKDIR /code

#将当前目录（即 Dockerfile所在的目录）的所有文件和目录复制到容器内的/app目录中
COPY ../* /code
#编译项目
RUN mvn clean package -B -Dmaven.test.skip=true

#运行时镜像
FROM openjdk:8-jdk-alpine

# 设置应用工作目录
WORKDIR /app

# 将构建产物拷贝到运行时的工作目录中（从之前命名为 build 的构建阶段中复制构建产物到当前阶段的当前目录下）
COPY --from=build /app/dubbo-provider/target/*.jar ./

# 服务暴露的端口
EXPOSE 20880

# 启动命令
ENTRYPOINT ["java","-jar","/app/dubbo-provider.jar"]