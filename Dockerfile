FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER root
RUN mkdir -p /app
USER spring:spring