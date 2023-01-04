FROM adoptopenjdk/openjdk11 as builder

ARG BACKEND_WORKDIR
WORKDIR ${BACKEND_WORKDIR}

COPY . .
RUN ./gradlew clean && ./gradlew build

FROM adoptopenjdk/openjdk11

ARG BACKEND_WORKDIR
WORKDIR ${BACKEND_WORKDIR}

#COPY . .
#ENTRYPOINT java -jar ./build/libs/wiki-0.0.1-SNAPSHOT.jar

COPY --from=builder . .
ENTRYPOINT java -jar ./build/libs/wiki-0.0.1-SNAPSHOT.jar
