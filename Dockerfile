FROM maven AS maven-container

RUN mkdir /usr/src/app
WORKDIR /usr/src/app

COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM java:8-jdk-alpine
RUN adduser -Dh /home/bfwg bfwg
WORKDIR /app
COPY --from=maven-container /usr/src/app/target/chauchau-server-0.1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/app/chauchau-server-0.1.0-SNAPSHOT.jar"]

