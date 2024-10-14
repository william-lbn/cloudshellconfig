# 使用官方 OpenJDK 19 镜像作为基础镜像
FROM openjdk:19-jdk-alpine AS build

# 安装 Maven
RUN apk add --no-cache curl bash \
    && mkdir -p /usr/share/maven /usr/share/maven/ref \
    && curl -fsSL -o /tmp/apache-maven.tar.gz https://archive.apache.org/dist/maven/maven-3/3.9.3/binaries/apache-maven-3.9.3-bin.tar.gz \
    && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn \
    && rm -rf /tmp/apache-maven.tar.gz

# 设置 Maven 环境变量
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 并下载依赖
COPY pom.xml .
RUN mvn dependency:go-offline

# 复制项目源码
COPY src ./src

# 构建项目
RUN mvn clean package -DskipTests

# 使用官方 OpenJDK 19 运行时作为运行阶段
FROM openjdk:19-jdk-alpine

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=build /app/target/*.jar app.jar

# 暴露端口（根据需要修改）
EXPOSE 8080

# 运行应用
CMD ["java","-jar","-Dspring.config.location=application.yaml","-Dk8s-config.kubeConfigPath=/k8s-config/cluster/config","-Dk8s-config.kubeConfigPath=/k8s-config/usersystem/config","app.jar"]
