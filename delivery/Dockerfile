FROM gradle:jdk17

COPY --chown=gradle:gradle . /app
WORKDIR /app
#RUN ./gradlew build

EXPOSE 8084
WORKDIR /app

CMD java -jar ./build/libs/delivery-0.0.1-SNAPSHOT.jar

