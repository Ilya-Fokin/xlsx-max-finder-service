package org.example.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Service
public class XlsxService {

    public static final String INCORRECT_INPUT_RANK_ERROR_MESSAGE = "Incorrect input rank %d, there are %d object in total";
    private final XlsxReader reader;

    /**
     * Конструктор для инициализации сервиса с XlsxReader.
     */
    public XlsxService() {
        this.reader = new XlsxReader();
    }

    /**
     * Находит N-ое максимальное уникальное число из файла XLSX.
     *
     * @param path Путь к файлу XLSX.
     * @param rank Позиция (N-ое) максимального числа.
     * @return N-ое максимальное уникальное число.
     * @throws FileNotFoundException если файл не найден по указанному пути.
     * @throws IllegalArgumentException если rank некорректен.
     */
    public int finderMaxNumber(String path, int rank) throws FileNotFoundException {
        Set<Integer> numbers = reader.readUniqueNumbers(path);
        if (rank <= 0 || rank > numbers.size()) {
            String message = format(INCORRECT_INPUT_RANK_ERROR_MESSAGE, rank, numbers.size());
            throw new IllegalArgumentException(message);
        }
        List<Integer> sortedNumbers = mergeSort(new ArrayList<>(numbers));
        return sortedNumbers.get(sortedNumbers.size() - rank);
    }

    /**
     * Сортирует список целых чисел с использованием сортировки слиянием.
     *
     * @param numbers Список целых чисел для сортировки.
     * @return Отсортированный список целых чисел.
     */
    private List<Integer> mergeSort(List<Integer> numbers) {
        if (numbers.size() <= 1) {
            return numbers;
        }

        int mid = numbers.size() / 2;
        List<Integer> left = mergeSort(numbers.subList(0, mid));
        List<Integer> right = mergeSort(numbers.subList(mid, numbers.size()));

        return merge(left, right);
    }


    /**
     * Объединяет два списка целых чисел в один отсортированный список.
     *
     * @param left Левый отсортированный список.
     * @param right Правый отсортированный список.
     * @return Объединенный отсортированный список.
     */
    private List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }

        return merged;
    }
}
