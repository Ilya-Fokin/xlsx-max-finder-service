package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.exception.XlsxReaderProcessingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

/**
 * Работа с файлами формата xlsx
 */
@Slf4j
public class XlsxReader {

    public static final String SHEET_PROCESSING_COMPLETE_INFO_MESSAGE = "The sheet {} has been processed, find {} numbers";
    public static final String READING_FILE_ERROR_MESSAGE = "Error while reading the file %s";
    public static final String INCORRECT_INPUT_PATH_MESSAGE = "Incorrect input path %s";

    /**
     * Читает уникальные целые числа из указанного файла XLSX.
     *
     * @param path Путь к файлу XLSX.
     * @return Множество уникальных целых чисел.
     * @throws FileNotFoundException если файл не найден по указанному пути.
     */
    public Set<Integer> readUniqueNumbers(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            String message = format(INCORRECT_INPUT_PATH_MESSAGE, path);
            throw new FileNotFoundException(message);
        }
        try (FileInputStream fis = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fis)) {

            int sheetCount = workbook.getNumberOfSheets();

            Set<CompletableFuture<Set<Integer>>> futures = IntStream.range(0, sheetCount)
                    .mapToObj(i -> readSheet(workbook.getSheetAt(i)))
                    .collect(Collectors.toSet());

            return futures.stream()
                    .flatMap(future -> future.join().stream())
                    .collect(Collectors.toSet());
        } catch (IOException ex) {
            String message = format(READING_FILE_ERROR_MESSAGE, path);
            throw new XlsxReaderProcessingException(message, ex);
        }
    }

    /**
     * Читает целые числа из указанного листа Excel в асинхронном режиме.
     *
     * @param sheet Лист Excel для чтения.
     * @return CompletableFuture, представляющий множество целых чисел.
     */
    private CompletableFuture<Set<Integer>> readSheet(Sheet sheet) {
        return CompletableFuture.supplyAsync(() -> {
            Set<Integer> numbers = new HashSet<>();
            for (Row row : sheet) {
                row.forEach(cell -> {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        numbers.add((int) cell.getNumericCellValue());
                    }
                });
            }
            log.info(SHEET_PROCESSING_COMPLETE_INFO_MESSAGE, sheet.getSheetName(), numbers.size());
            return numbers;
        });
    }
}
