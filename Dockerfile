FROM openjdk:13-alpine
MAINTAINER Birhane Tinsae <birhane.tinsaa@gmail.com>
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ENV SPRING_PROFILES_ACTIVE=prod
ENV SECRET_KEY=$SECRET_KEY
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p /home/spring/uploads
RUN mkdir /home/spring/logs

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]