FROM openjdk:11
ARG JAR_FILE=./build/libs/cocobob-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} myboot.jar
CMD java -jar -Dspring.profiles.active=${active} cocobob-0.0.1-SNAPSHOT.jar