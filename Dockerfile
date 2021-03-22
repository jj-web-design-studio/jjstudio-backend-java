FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER root
RUN mkdir -p /app
USER spring:spring
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005","-jar","/app/jj-studio-0.1.0.jar"]