# 使用官方 Java 镜像作为基础镜像
FROM openjdk:8-jdk-slim

# 设置工作目录
WORKDIR /app

# 添加应用 JAR 文件
ADD target/jiangYang-module-iot-plugin-emqx.jar /app/app.jar

# 暴露服务使用的端口
EXPOSE 8100 8101

# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置Java启动参数
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"

# 设置启动命令
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]