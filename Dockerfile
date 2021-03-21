FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER root
RUN mkdir -p /app/libs
USER spring:spring
ARG JAR_FILE=build/libs/jj-studio-0.1.0.jar
COPY ${JAR_FILE} /app/jj-studio-0.1.0.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005","-jar","/app/jj-studio-0.1.0.jar"]