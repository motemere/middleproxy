FROM openjdk:17-slim

RUN apt-get update && apt-get install -y make

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN make build

CMD ["java", "-jar","build/libs/middleproxy-0.0.1-SNAPSHOT.jar"]
