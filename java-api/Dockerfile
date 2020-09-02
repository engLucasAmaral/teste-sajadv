FROM maven:3-alpine  AS build-env
WORKDIR /build

COPY src /build/src
COPY pom.xml /build/

RUN mvn -B -DskipTests clean package  -Dprofile=docker
#RUN mvn release:prepare  --batch-mode -DreleaseVersion=1.0.0 -DdevelopmentVersion=1.0.1-SNAPSHOT release:perform  -Dprofile=docker


FROM anapsix/alpine-java:latest
RUN mkdir app
WORKDIR /app

#TIMEZONE
RUN apk add tzdata && \
 cp /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && \
 apk del tzdata

RUN apk -U add curl
RUN curl -OLs https://repo1.maven.org/maven2/fish/payara/extras/payara-micro/5.2020.2/payara-micro-5.2020.2.jar
RUN curl -OLs https://repo1.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/2.4.1/mariadb-java-client-2.4.1.jar

COPY --from=build-env /build/target/sajadv-api-1.0.0-SNAPSHOT app.war
COPY deployments/wait-for-it.sh .
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["java",\
    "-Dproduct.name=API SAJADV",\
    "-XX:+UnlockExperimentalVMOptions",\
    "-XX:+UseCGroupMemoryLimitForHeap",\
    "-Dfile.encoding=UTF-8",\
    "-jar","payara-micro-5.2020.2.jar",\
    "--rootDir","domain",\
    "--deploy","app.war:sajadv",\
    "--addJars","mariadb-java-client-2.4.1.jar",\
    "--postbootcommandfile","postBootCommands.txt"]