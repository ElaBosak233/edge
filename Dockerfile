# 第一阶段：构建前端
FROM node:18 AS frontend-builder

# 设置工作目录
WORKDIR /app

# 复制前端文件
COPY client/package*.json ./
COPY client/ ./

# 安装前端依赖并构建前端
RUN npm install && npm run build

# 第二阶段：构建后端并将前端文件复制到后端静态资源目录
FROM openjdk:21-jdk-slim AS backend-builder

# 设置工作目录
WORKDIR /app

# 构建后端
RUN ./mvnw clean package -DskipTests

# 运行阶段：将构建好的前端和后端文件合并到一个镜像中
FROM openjdk:21-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制构建好的后端jar文件
COPY --from=backend-builder /app/target/*.jar app.jar

# 复制前端构建结果到后端的静态资源目录
COPY --from=frontend-builder /app/build /dist

# 暴露端口
EXPOSE 8080

# 运行Spring Boot应用
ENTRYPOINT ["java", "-jar", "app.jar"]
