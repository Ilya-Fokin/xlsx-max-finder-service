package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.example.model.CustomApiResponse;
import org.example.service.XlsxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

/**
 * Контроллер для работы с файлами XLSX.
 */
@RestController()
@RequestMapping("/xlsx")
public class XlsxContoller {

    private final XlsxService xlsxService;

    /**
     * Конструктор контроллера.
     *
     * @param xlsxService Сервис для работы с файлами XLSX.
     */
    public XlsxContoller(XlsxService xlsxService) {
        this.xlsxService = xlsxService;
    }

    /**
     * Найти N-ное максимальное число из файла.
     *
     * @param path   Путь к файлу xlsx.
     * @param rank   N-ое число от наибольшего.
     * @param request HTTP-запрос для получения информации о запросе.
     * @return ResponseEntity с результатом запроса.
     * @throws FileNotFoundException если файл не найден.
     */
    @Operation(summary = "Найти N-ное максимальное число из файла")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Файл не найден",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные параметры",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервиса",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/max")
    public ResponseEntity findMaxNumber(
            @Parameter(description = "Путь к файлу xlsx", example = "C:/Users/Ilya/Desktop/Книга1.xlsx")
            @RequestParam(value = "path") String path,
            @Parameter(description = "N-ое число от наибольшего", example = "5")
            @RequestParam(value = "rank") int rank,
            HttpServletRequest request) throws FileNotFoundException {
        int number = xlsxService.finderMaxNumber(path, rank);
        String url = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        return CustomApiResponse.success(url, number);
    }
}
