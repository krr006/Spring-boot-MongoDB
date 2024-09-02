# Текстовая библиотека

## Описание

Это веб-приложение разработано с использованием Spring Boot и React. Оно позволяет пользователям регистрироваться, авторизовываться и управлять своими текстовыми записями. Приложение предоставляет возможность создавать, удалять, редактировать и искать текстовые записи.

## Функциональность

- Регистрация и авторизация пользователей.
- Создание текстовых записей.
- Удаление созданных текстовых записей.
- Редактирование текстовых записей.
- Поиск текстовых записей по ID и содержимому.
- Отображение полного списка созданных пользователем текстовых записей.

## Технические требования

### Backend
- Java 17
- Spring Boot
- Maven
- PostgreSQL (для хранения данных пользователей)
- MongoDB (для хранения текстовых записей)

### Frontend
- Node.js 18.x
- React

## Инструкция по развёртыванию

### 1. Подготовка окружения

#### Установите необходимые инструменты:

1. **Java 17**: Убедитесь, что Java 17 установлена на вашей машине.
    - [Инструкция по установке](https://adoptium.net/temurin/releases/?version=17)

2. **Maven**: Убедитесь, что Apache Maven установлен.
    - [Инструкция по установке](https://maven.apache.org/install.html)

3. **PostgreSQL**: Установите PostgreSQL для хранения данных пользователей.
    - [Инструкция по установке](https://www.postgresql.org/download/)

4. **MongoDB**: Установите MongoDB для хранения текстовых записей.
    - [Инструкция по установке](https://www.mongodb.com/try/download/community)

5. **Node.js и npm**: Установите Node.js версии 18 и выше.
    - [Инструкция по установке](https://nodejs.org/)

### 2. Клонирование репозитория

Перед началом установки клонируйте репозиторий с исходным кодом приложения:

    ```bash
    git clone https://github.com/krr006/Spring-boot-MongoDB.git
    cd Spring-boot-MongoDB

### 3. Настройка базы данных

#### PostgreSQL:

1. Запустите PostgreSQL и создайте новую базу данных и таблицы:

   ```sql
   -- Создание базы данных
   CREATE DATABASE postgres;

   -- Подключение к базе данных
   \c postgres;

   -- Создание таблицы для пользователей
   CREATE TABLE app_users (
       id SERIAL PRIMARY KEY,
       email VARCHAR(255) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL,
       username VARCHAR(255) NOT NULL UNIQUE,
       role VARCHAR(50) NOT NULL
   );

2. Обновите настройки подключения в `application.yaml`:
    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/postgres
        username: postgres
        password: password
        driver-class-name: org.postgresql.Driver

#### MongoDB:

1. Запустите MongoDB на порту по умолчанию (`27017`).
2. Обновите настройки подключения в `application.yaml`: 
    ```yaml
   spring:
     data:
       mongodb:
         host: localhost
         port: 27017
         database: text_record_db

### 4. Запуск бэкенда (Spring Boot)

1. Перейдите в директорию `project-test`:
    ```bash
   cd project-test

2. Соберите проект с помощью Maven:
    ```bash
    mvn clean package

3. Запустите Spring Boot приложение:
    ```bash
    mvn spring-boot:run

### 5. Запуск фронтенда (React)

1. Перейдите в директорию `sec-app`:

2. Установите зависимости:
    ```bash
    npm install

3. Запустите фронтенд-приложение:
    ```bash
    npm start

Фронтенд будет доступен по адресу `http://localhost:3000`.





