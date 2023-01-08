FROM adoptopenjdk/openjdk11 as builder

WORKDIR /build

COPY . .
RUN ./gradlew clean && ./gradlew build --info

FROM adoptopenjdk/openjdk11

WORKDIR /app

#COPY . .
#ENTRYPOINT java -jar ./build/libs/wiki-0.0.1-SNAPSHOT.jar

COPY --from=builder /build/build/libs/wiki-0.0.1-SNAPSHOT.jar /app/server.jar
CMD java -jar /app/server.jar
