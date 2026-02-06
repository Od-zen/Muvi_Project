# Видеомагазин (Video Shop) - Spring Boot приложение

## 📋 Описание проекта

Веб-приложение для управления каталогом видео (фильмов, сериалов) с категоризацией по жанрам.

## 🚀 Установка и запуск

### Предварительные требования
- Java 17+
- Oracle Database (или замените на H2/PostgreSQL)
- Maven 3.6+

## ПРОЦЕСС УСТАНОВКИ

1. **Копируй/клонируй**

2. **Настроим БД** в `application.properties`:
```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=ваш_пользователь
spring.datasource.password=ваш_пароль
```

3. **Инициализируйте БД**:
```sql
-- Выполните скрипт init_database.sql в Oracle SQL Developer
@init_database.sql
```

4. **Скопируйте файлы в проект**:
```
ItemWithCategoryRepository.java → src/main/java/jp/co/sss/shop/repository/
CategoryRepository.java → src/main/java/jp/co/sss/shop/repository/
ItemWithCategoryController.java → src/main/java/jp/co/sss/shop/controller/
Category.java → src/main/java/jp/co/sss/shop/entity/
ItemWithCategory.java → src/main/java/jp/co/sss/shop/entity/
application.properties → src/main/resources/
```

5. **Запустите приложение**:
```bash
cd C:\Code\shop\Muvi_Project\shop
mvn clean install
mvn spring-boot:run
```

6. **Откройте в браузере**: http://localhost:8080/videos

## 📌 API Endpoints

### Основные маршруты
- `GET /videos` - Все видео
- `GET /videos/search/{id}` - Поиск по ID
- `GET /videos/category/{categoryId}` - Фильмы по категории

### Поиск по категориям
- `GET /videos/search/category/form` - Форма поиска
- `GET /videos/search/category?categoryId=1` - Поиск по ID категории
- `GET /videos/search/category-name/form` - Форма поиска по имени
- `GET /videos/search/category-name?categoryName=Боевик` - Поиск по имени

### Поиск по цене
- `GET /videos/search/price/form` - Форма поиска
- `GET /videos/search/price/min?price=1000` - Видео дороже 1000
- `GET /videos/search/price/max?price=2000` - Видео дешевле 2000
- `GET /videos/search/price/range?min=1000&max=2000` - В диапазоне

### JPQL поиск
- `GET /videos/jpql/form` - Форма JPQL поиска
- `GET /videos/jpql/search?id=1` - Поиск через Named Query

## 🎬 Примеры данных

### Категории (Жанры)
1. Боевик
2. Комедия
3. Драма
4. Фантастика
5. Ужасы
6. Документальный

### Видео
- Матрица (1500₽, Боевик)
- Джон Уик (1200₽, Боевик)
- Один дома (800₽, Комедия)
- Побег из Шоушенка (2000₽, Драма)
- Интерстеллар (2200₽, Фантастика)
- И другие...

## 🔄 Миграция на другую БД

### Для H2 (in-memory, для тестов):
```properties
# application.properties
spring.datasource.url=jdbc:h2:mem:videoshop
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

```xml
<!-- pom.xml -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Для PostgreSQL:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/videoshop
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## 📝 Лучшие практики, которые применены

1. ✅ **Dependency Injection** через конструктор
2. ✅ **REST-подобные URL** (`/videos` вместо `/items`)
3. ✅ **Использование `@GetMapping`** вместо `@RequestMapping(method = GET)`
4. ✅ **JavaDoc** для документирования кода
5. ✅ **Выделение общей логики** в приватные методы
6. ✅ **Type Safety** (`TypedQuery<T>` вместо сырых Query)
7. ✅ **Валидация на уровне БД** (nullable, unique)
8. ✅ **Meaningful naming** (говорящие имена методов и переменных)

## ⚠️ Возможные проблемы и решения

### Проблема: Приложение не стартует
**Решение**: Проверьте что все репозитории скопированы в правильные директории

### Проблема: Ошибка подключения к БД
**Решение**: Проверьте credentials в `application.properties`

### Проблема: Таблицы не создаются
**Решение**: Выполните `init_database.sql` вручную или используйте `spring.jpa.hibernate.ddl-auto=create`

## 📞 Поддержка

При возникновении вопросов проверьте:
1. Логи приложения
2. Корректность SQL скриптов
3. Настройки подключения к БД