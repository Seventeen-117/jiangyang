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
ENV SPRING_PROFILES_ACTIVE="prod"
ENV UPSTREAM_URL=""
ENV NACOS_SERVER_ADDR=""
ENV NACOS_NAMESPACE=""

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8092/actuator/health || exit 1

# 切换到非root用户
USER appuser

# 启动命令
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} \
    -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
    -DjiangYang.iot.plugin.common.upstream-url=${UPSTREAM_URL:-http://gateway:48080} \
    -Dspring.cloud.nacos.server-addr=${NACOS_SERVER_ADDR} \
    -Dspring.cloud.nacos.discovery.namespace=${NACOS_NAMESPACE} \
    -cp app:app/lib/*:app/META-INF org.springframework.boot.loader.JarLauncher"]