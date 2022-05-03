FROM openjdk:16.0-buster
VOLUME /tmp
EXPOSE 8787
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
CMD java -Xms512m -Xmx1024m -jar app.jar