FROM registrynexus.lab.data.com.py/maven:3.8.1-openjdk-8-slim as build-step
WORKDIR /app
COPY . /app
RUN apt-get update; apt-get install -y fontconfig libfreetype6
RUN mvn clean package install -DskipTests

FROM registrynexus.lab.data.com.py/openjdk:8u302-slim
COPY --from=build-step /app/target/Reportes-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","Reportes-0.0.1-SNAPSHOT.jar"]
