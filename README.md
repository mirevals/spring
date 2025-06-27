# Student Token Management System

Spring MVC приложение для управления жетонами студентов.

## Описание

Система позволяет управлять жетонами студентов с различными уровнями доступа:
- **Студенты** могут только уменьшать количество жетонов
- **Преподаватели** могут увеличивать и уменьшать жетоны, а также отчислять студентов

## API Endpoints

### 1. Проверка статуса приложения
```
GET /api/v1/getStatus
```
**Ответ:**
```json
{
  "success": true,
  "message": "Application is running",
  "data": null
}
```

### 2. Получение всех студентов
```
GET /api/v1/students
```
**Ответ:**
```json
{
  "success": true,
  "message": "Students retrieved successfully",
  "data": [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "tokens": 5
    }
  ]
}
```

### 3. Получение студента по ID
```
GET /api/v1/students/{id}
```
**Пример:** `GET /api/v1/students/1`

### 4. Поиск студента по имени
```
GET /api/v1/students/search?firstName=John&lastName=Doe
```

### 5. Добавление нового студента
```
POST /api/v1/students
```
**Тело запроса:**
```json
{
  "firstName": "New",
  "lastName": "Student",
  "userRole": "TEACHER"
}
```

### 6. Обновление жетонов
```
POST /api/v1/students/tokens
```
**Тело запроса:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "userRole": "TEACHER",
  "tokenChange": 5
}
```

### 7. Удаление студента
```
DELETE /api/v1/students
```
**Тело запроса:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "userRole": "TEACHER"
}
```

## Правила доступа

### Студенты (STUDENT)
- ✅ Могут просматривать список студентов
- ✅ Могут уменьшать количество жетонов (отрицательные значения)
- ❌ Не могут увеличивать жетоны
- ❌ Не могут отчислять студентов
- ❌ Не могут создавать студентов с жетонами > 0

### Преподаватели (TEACHER)
- ✅ Могут выполнять все операции
- ✅ Могут увеличивать и уменьшать жетоны
- ✅ Могут отчислять студентов
- ✅ Могут создавать студентов с любым количеством жетонов

## Запуск приложения

1. Убедитесь, что у вас установлена Java 17
2. Выполните сборку проекта:
   ```bash
   ./gradlew build
   ```
3. Запустите приложение на сервере приложений (Tomcat, Jetty, etc.)

## Тестирование

Для запуска тестов выполните:
```bash
./gradlew test
```

## Структура проекта

```
src/
├── main/
│   ├── java/
│   │   ├── config/          # Конфигурация Spring
│   │   ├── controller/      # REST контроллеры
│   │   ├── model/          # Модели данных и DTO
│   │   ├── repository/     # Репозитории для работы с данными
│   │   ├── service/        # Бизнес-логика
│   │   └── security/       # Система безопасности
│   ├── resources/
│   │   ├── application.properties
│   │   └── students.csv    # Файл с данными студентов
│   └── webapp/
│       └── WEB-INF/
│           ├── web.xml
│           └── springmvc-servlet.xml
└── test/
    └── java/
        ├── controller/     # Тесты контроллеров
        └── service/        # Тесты сервисов
```

## Логирование

Все действия пользователей записываются в файл `logs/actions.log` в формате:
```
[2024-01-01T10:00:00] John Doe (TEACHER): UPDATE_TOKENS | Changed tokens by: 5
```
