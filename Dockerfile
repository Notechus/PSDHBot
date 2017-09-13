# Dockerfile
FROM maven:3.5-jdk-8
MAINTAINER Notechus <seba0713@gmail.com>

ENV JAVA_VER 8

WORKDIR /app

ADD /target/psdhbot-jar-with-dependencies.jar /app/psdh-bot.jar
ADD /config.yml /app/config.yml
ENTRYPOINT ["java","-jar","psdh-bot.jar"]