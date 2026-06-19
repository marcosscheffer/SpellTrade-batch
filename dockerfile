FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw package -DskipTests

ENTRYPOINT ["java", "-jar", "target/cards-batch-0.0.1-SNAPSHOT.jar"]