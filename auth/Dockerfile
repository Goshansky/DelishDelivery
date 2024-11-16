FROM gradle:jdk17

COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN ./gradlew build

EXPOSE 8080
WORKDIR /app

CMD java -jar ./build/libs/auth-0.0.1-SNAPSHOT.jar

