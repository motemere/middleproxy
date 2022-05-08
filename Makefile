.DEFAULT_GOAL := build

clean:
	./gradlew clean

build: clean
	./gradlew build

lint:
	./gradlew checkstyleMain checkstyleTest

test:
	./gradlew test
