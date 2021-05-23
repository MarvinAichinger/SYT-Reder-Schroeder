FROM openjdk:11-jdk AS builder
WORKDIR /usr/src/echo-server
COPY ./EchoServer.java .

RUN javac EchoServer.java


FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /usr/src/echo-server/EchoServer.class .
EXPOSE 9001
CMD ["java", "EchoServer"]