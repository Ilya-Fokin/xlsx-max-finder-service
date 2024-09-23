package org.example.model.exception;


/**
 * Исключение, возникающее при обработке файла XLSX.
 * Это исключение расширяет RuntimeException и используется для
 * обработки ошибок, связанных с чтением и обработкой данных из файла XLSX.
 */
public class XlsxReaderProcessingException extends RuntimeException {

    /**
     * Конструктор исключения.
     *
     * @param message Сообщение об ошибке.
     * @param cause   Причина возникновения исключения.
     */
    public XlsxReaderProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
