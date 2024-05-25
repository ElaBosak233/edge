# Frontend
FROM node:20 AS frontend-builder

WORKDIR /app

COPY client/ ./

RUN npm install 
RUN npm run build

# Backend
FROM openjdk:21-jdk-slim AS backend-builder

WORKDIR /app

COPY ./ ./

RUN chmod 777 ./mvnw

RUN ./mvnw clean package -DskipTests

# Production
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=backend-builder /app/target/*.jar app.jar

COPY --from=frontend-builder /app/dist dist

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
