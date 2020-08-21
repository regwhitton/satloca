FROM oracle/graalvm-ce:20.1.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/satloca
WORKDIR /home/app/satloca

RUN native-image --no-server -cp build/libs/satloca-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/satloca/satloca /app/satloca
ENTRYPOINT ["/app/satloca"]
