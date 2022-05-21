.DEFAULT_GOAL := build

clean:
	./gradlew clean

build: clean
	./gradlew build -x test -x checkstyleMain -x checkstyleTest

lint:
	./gradlew checkstyleMain checkstyleTest

test:
	./gradlew test

docker-build:
	docker build --build-arg BUILD_VERSION=0.0.2-SNAPSHOT -t motemere/testproject-middleproxy:latest -t motemere/testproject-middleproxy:0.0.2-SNAPSHOT .
