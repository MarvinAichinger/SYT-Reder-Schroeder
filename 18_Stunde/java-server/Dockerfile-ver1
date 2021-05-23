FROM openjdk
WORKDIR /usr/src/echo-server
COPY ./EchoServer.java .
EXPOSE 4000
RUN javac EchoServer.java
CMD ["java", "EchoServer"]