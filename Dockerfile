# 使用官方的 OpenJDK 22 作为基础镜像
FROM eclipse-temurin:22-jdk-focal as build-backend

# 设置工作目录
WORKDIR /app

# 将后端项目复制到容器中
COPY . /app

# 编译后端项目
RUN ./mvnw clean package -DskipTests

# 从基础镜像开始构建前端
FROM node:16 as build-frontend

# 设置工作目录
WORKDIR /app/client

# 将前端项目复制到容器中
COPY ./client /app/client

# 安装前端依赖
RUN npm install

# 构建前端项目
RUN npm run build

# 最终镜像，使用 OpenJDK 22
FROM eclipse-temurin:22-jdk-focal

# 设置工作目录
WORKDIR /app

# 将后端jar包复制到容器中
COPY --from=build-backend /app/target/*.jar /app/app.jar

# 添加前端构建产物
COPY --from=build-frontend /app/client/dist /app/dist

# 暴露端口
EXPOSE 8080

# 运行 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]
