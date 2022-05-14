# build container
FROM gradle:7.4.1-jdk17-alpine AS TEMP_BUILD_IMAGE

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY build.gradle settings.gradle $APP_HOME  
COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src

USER root

RUN chown -R gradle /home/gradle/src    

COPY . .

RUN gradle clean build -x test -x checkstyleMain -x checkstyleTest

# target container
FROM openjdk:17-slim

ARG BUILD_VERSION

ENV ARTIFACT_NAME=middleproxy-${BUILD_VERSION}.jar
ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

EXPOSE 8090

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}
