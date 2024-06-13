FROM eclipse-temurin:21-jdk-alpine
COPY ./src ./app
WORKDIR /build
COPY ./target/bot.jar app.jar
EXPOSE 8090
CMD ["java", "-jar", "app.jar"]
