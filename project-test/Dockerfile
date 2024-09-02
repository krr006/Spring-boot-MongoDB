FROM eclipse-temurin:17-jdk-alpine
LABEL authors="kafka"

# Используем официальный образ Java 17 в качестве базового

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файл сборки приложения (например, JAR-файл) в контейнер
COPY target/project-test-0.0.1-SNAPSHOT.jar /app/application.jar

# Указываем команду запуска приложения
ENTRYPOINT ["java", "-jar", "/app/application.jar"]

# Указываем порт, который будет использовать приложение (если используется, например, порт 8080)
EXPOSE 8080

