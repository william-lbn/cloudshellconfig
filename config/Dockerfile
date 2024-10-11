# 使用官方 Maven 镜像作为构建阶段
FROM maven:3.9.3-openjdk-19 AS build

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 并下载依赖
COPY pom.xml .
RUN mvn dependency:go-offline

# 复制项目源码
COPY src ./src

# 构建项目
RUN mvn clean package -DskipTests

# 使用官方 OpenJDK 运行时作为运行阶段
FROM openjdk:19-jdk-alpine

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=build /app/target/*.jar app.jar

# 暴露端口（根据需要修改）
EXPOSE 8080

# 运行应用
ENTRYPOINT ["java","-jar","app.jar"]
