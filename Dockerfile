FROM eclipse-temurin:21

WORKDIR /app

COPY website/ .

RUN chmod +x mvnw

RUN ./mvnw spring-boot:repackage package -DskipTests

EXPOSE 8080

CMD ["java","-jar","target/website-0.0.1-SNAPSHOT.jar"]