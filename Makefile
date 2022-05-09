.DEFAULT_GOAL := build

clean:
	./gradlew clean

build: clean
	./gradlew build -x test

lint:
	./gradlew checkstyleMain checkstyleTest

test:
	./gradlew test
