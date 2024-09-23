package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Класс для представления стандартизированного API ответа.
 */
@Getter
@Setter
public class CustomApiResponse {

    private String path;

    private String requestId;

    private OffsetDateTime timeStamp;

    private Object data;

    /**
     * Конструктор для создания нового ответа API.
     *
     * @param path путь запроса, который привел к данному ответу
     * @param data данные, возвращаемые в ответе
     */
    public CustomApiResponse(String path, Object data) {
        this.path = path;
        this.requestId = UUID.randomUUID().toString();
        this.timeStamp = OffsetDateTime.now();
        this.data = data;
    }

    /**
     * Создает успешный ответ API с кодом состояния 200 OK.
     *
     * @param path путь запроса, который привел к данному ответу
     * @param data данные, возвращаемые в ответе
     * @return объект ResponseEntity, содержащий CustomApiResponse и HTTP статус 200 OK
     */
    public static ResponseEntity<CustomApiResponse> success(String path, Object data) {
        return ResponseEntity.ok().body(new CustomApiResponse(path, data));
    }

    /**
     * Создает ошибочный ответ API с указанным кодом состояния.
     *
     * @param path путь запроса, который привел к данному ответу
     * @param code HTTP статус код для ответа
     * @param data данные, объясняющие причину ошибки
     * @return объект ResponseEntity, содержащий CustomApiResponse и указанный HTTP статус код
     */
    public static ResponseEntity<CustomApiResponse> error(String path, HttpStatusCode code, Object data) {
        return new ResponseEntity<>(new CustomApiResponse(path, data), code);
    }
}
