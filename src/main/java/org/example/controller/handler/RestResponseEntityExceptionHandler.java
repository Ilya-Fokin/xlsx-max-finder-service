package org.example.controller.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.model.CustomApiResponse;
import org.example.model.exception.XlsxReaderProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.util.Date;


/**
 * Обработчик исключений для контроллеров.
 * Перехватывает и обрабатывает различные исключения, возвращая кастомные ответы.
 */
@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Стандартное сообщение об ошибке.
     */
    public static final String DEFAULT_ERROR_MESSAGE = "Возникла ошибка {}, сообщение: {}, время: {}";

    /**
     * Обработка всех RuntimeException
     */
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<CustomApiResponse> handleAllException(Exception ex,
                                                                   HttpServletRequest request) {
        String path = request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        log.error(DEFAULT_ERROR_MESSAGE, XlsxReaderProcessingException.class,
                ex.getMessage(), new Date());
        return CustomApiResponse.error(
                path,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
    }

    /**
     * Обработка исключений XlsxReaderProcessingException.
     *
     * @param ex      Исключение, которое произошло.
     * @param request HTTP-запрос, в котором произошло исключение.
     * @return ResponseEntity с кастомным ответом.
     */
    @ExceptionHandler(XlsxReaderProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomApiResponse> handleXlsxReaderProcessingException(
            XlsxReaderProcessingException ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        log.error(DEFAULT_ERROR_MESSAGE, XlsxReaderProcessingException.class,
                ex.getMessage(), new Date());
        return CustomApiResponse.error(
                path,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
    }

    /**
     * Обработка исключений FileNotFoundException.
     *
     * @param ex      Исключение, которое произошло.
     * @param request HTTP-запрос, в котором произошло исключение.
     * @return ResponseEntity с кастомным ответом.
     */
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomApiResponse> handleFileNotFoundException(
            FileNotFoundException ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        log.error(DEFAULT_ERROR_MESSAGE, XlsxReaderProcessingException.class,
                ex.getMessage(), new Date());
        return CustomApiResponse.error(
                path,
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    /**
     * Обработка исключений IllegalArgumentException.
     *
     * @param ex      Исключение, которое произошло.
     * @param request HTTP-запрос, в котором произошло исключение.
     * @return ResponseEntity с кастомным ответом.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomApiResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        log.error(DEFAULT_ERROR_MESSAGE, IllegalArgumentException.class,
                ex.getMessage(), new Date());
        return CustomApiResponse.error(
                path,
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }
}
