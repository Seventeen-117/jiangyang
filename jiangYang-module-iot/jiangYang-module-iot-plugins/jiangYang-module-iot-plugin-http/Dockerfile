FROM openjdk:8-jdk-slim as builder

WORKDIR /build
# 假设您的JAR包在target目录下
COPY ./target/jiangYang-module-iot-plugin-http.jar ./app.jar
# 提取JAR中的依赖和类
RUN mkdir -p dependency && (cd dependency && jar -xf ../app.jar)

FROM openjdk:8-jdk-slim

WORKDIR /app
# 创建非root用户
RUN addgroup --system --gid 1001 appuser && \
    adduser --system --uid 1001 --gid 1001 appuser

# 从builder阶段复制解压的应用
COPY --from=builder /build/dependency/BOOT-INF/lib /app/lib
COPY --from=builder /build/dependency/META-INF /app/META-INF
COPY --from=builder /build/dependency/BOOT-INF/classes /app

# 设置时区
ENV TZ=Asia/Shanghai

# 暴露应用使用的端口
EXPOSE 8092 8093

# 设置环境变量
ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:+UseG1GC"
## 启动后端项目
CMD java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar app.jar