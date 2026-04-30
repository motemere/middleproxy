# build container
FROM gradle:9.4.0-jdk25-alpine AS temp_build_image

ARG GPR_USER
ARG GPR_TOKEN

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY . .

RUN gradle clean build -x test -x checkstyleMain -x checkstyleTest

# jlink — minimal JRE
FROM eclipse-temurin:25-jdk-alpine AS jre_build

COPY --from=temp_build_image /usr/app/build/libs/*.jar /app.jar

RUN jar xf /app.jar && \
    jdeps --ignore-missing-deps \
          --print-module-deps \
          --multi-release 25 \
          --recursive \
          --class-path 'BOOT-INF/lib/*' \
          /app.jar > /modules.txt && \
    jlink \
          --add-modules $(cat /modules.txt) \
          --strip-debug \
          --no-man-pages \
          --no-header-files \
          --compress=2 \
          --output /custom-jre

# target container
FROM alpine:3.21

ENV JAVA_HOME=/custom-jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=jre_build /custom-jre /custom-jre
COPY --from=temp_build_image /usr/app/build/libs/*.jar /app/app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
