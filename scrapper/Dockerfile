FROM eclipse-temurin:21-jdk-alpine
COPY ./src ./app
WORKDIR /build
COPY ./target/scrapper.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
