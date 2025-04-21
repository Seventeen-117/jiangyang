FROM eclipse-temurin:8-jre

# 创建目录并设置工作目录
WORKDIR /app

# 复制JAR文件
COPY ./target/jiangYang-module-iot-biz-2.4.1-jdk8-SNAPSHOT.jar /app/app.jar

# 确保JAR文件有执行权限
RUN chmod +x /app/app.jar

# 设置环境变量
ENV TZ=Asia/Shanghai 
ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"

# 暴露端口
EXPOSE 48091

# 启动命令 - 注意使用绝对路径
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]