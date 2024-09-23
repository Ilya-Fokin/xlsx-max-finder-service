Тестовое задание в компанию "Удобный софт"

Инструкция для запуска сервиса:
1. Собрать проект командой "mvn clean package"
2. Из папки с полученным .jar файлом, запустить сервис командой "java -jar xlsx-max-finder-service-1.0.jar".
   Имеющиеся переменные окружения: 
   WEB_PORT - порт сервиса (8082), 
   WEB_CONTEXT_PATH - контекстный путь (/api/v1),
   SPRING_PROFILE - профиль (console-logger),
   LOGGING_ROOT_LEVEL - уровень логирования root (INFO),
3. Перейти в swagger. Он находится по url: http://localhost:8082/api/v1/swagger-ui/index.html