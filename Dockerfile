FROM openjdk:11-jre-slim

WORKDIR /root

COPY ./cocobob-0.0.1-SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${active} cocobob-0.0.1-SNAPSHOT.jar