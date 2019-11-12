FROM adoptopenjdk/openjdk8
RUN apt-get update
RUN apt-get install -y apt-utils
RUN apt-get install -y net-tools
RUN mkdir /opt/app
ARG JAR_FILE
ADD target/tensorflow-java-1.0-jar-with-dependencies.jar /opt/app/app.jar
ADD src/main/resources/ferrari.jpg /opt/app/ferrari.jpg
COPY ./runMe.sh /
RUN chmod +x /runMe.sh
ENTRYPOINT ["/runMe.sh"]
EXPOSE 9000 9000
