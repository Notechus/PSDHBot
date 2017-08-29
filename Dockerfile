# Dockerfile
FROM maven:3.5-jdk-8
MAINTAINER Notechus <seba0713@gmail.com>

ENV JAVA_VER 8

WORKDIR /app

ADD /target/lib /app/lib
ADD /src/main/resources/dream_on.mp3 /app/dream_on.mp3
ADD /target/psdh-bot-1.0.jar /app/psdh-bot.jar
ADD /config.yml /app/config.yml
ENTRYPOINT ["java","-jar","psdh-bot.jar"]