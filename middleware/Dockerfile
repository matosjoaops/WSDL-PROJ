FROM maven:3.8.4-jdk-11-slim AS build
WORKDIR /
COPY . .
EXPOSE 8080
EXPOSE 8080/tcp
EXPOSE 8080/udp
RUN chmod a+x runScript.sh
RUN mvn package -Dmaven.test.skip
CMD ./runScript.sh
# set the startup command to execute the jar
#CMD ["java", "-jar",  "-Dspring.profiles.active=prod", "./target/back-end.jar", "production"]

