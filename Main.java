package aads.term_paper.binomial_heap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        BinomialHeap heap = new BinomialHeap();
        List<Integer> randomNumbers = generateRandomNumbers(10000); // Список случайных чисел, которые будут добавляться в биномиальную кучу
        List<Long> insertionTimes = new ArrayList<>(); // Временных интервалов, затраченных на каждую операцию вставки элемента
        List<Integer> insertionOperations = new ArrayList<>(); // Содержащий количество операций вставки
        List<Long> searchTimes = new ArrayList<>(); // Временных интервалов, затраченных на каждую операцию поиска минимального элемента
        List<Integer> searchOperations = new ArrayList<>(); // Содержащий количество операций поиска минимального элемента;
        List<Long> deletionTimes = new ArrayList<>(); // Временных интервалов, затраченных на каждую операцию удаления минимального элемента;
        List<Integer> deletionOperations = new ArrayList<>(); // Содержащий количество операций удаления минимального элемента

        // Добавление элементов в кучу
        for (int i = 0; i < randomNumbers.size(); i++) {
            int number = randomNumbers.get(i);
            long startTime = System.nanoTime();
            heap.insert(number);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            insertionTimes.add(elapsedTime);
            insertionOperations.add(i + 1);
        }

        // Поиск мin элемента
        List<Integer> randomIndexesForSearch = generateRandomIndexes(randomNumbers.size(), 100);
        for (int i = 0; i < randomIndexesForSearch.size(); i++) {
            int index = randomIndexesForSearch.get(i);
            int number = randomNumbers.get(index);
            long startTime = System.nanoTime();
            heap.findMinimum();
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            searchTimes.add(elapsedTime);
            searchOperations.add(i + 1);
        }

        // Удаление элементов
        List<Integer> randomIndexesForDeletion = generateRandomIndexes(randomNumbers.size(), 1000);
        for (int i = 0; i < randomIndexesForDeletion.size(); i++) {
            int index = randomIndexesForDeletion.get(i);
            int number = randomNumbers.get(index);
            long startTime = System.nanoTime();
            heap.deleteMinimum();
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            deletionTimes.add(elapsedTime);
            deletionOperations.add(i + 1);
        }

        // Вывод результатов
        System.out.println("Среднее время и количество операций для вставки:");
        printAverageTimeAndOperations(insertionTimes, insertionOperations);
        System.out.println("Среднее время и количество операций для поиска:");
        printAverageTimeAndOperations(searchTimes, searchOperations);
        System.out.println("Среднее время и количество операций для удаления:");
        printAverageTimeAndOperations(deletionTimes, deletionOperations);
    }

    private static List<Integer> generateRandomNumbers(int count) {
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            numbers.add(random.nextInt());
        }
        return numbers;
    }

    private static List<Integer> generateRandomIndexes(int maxIndex, int count) {
        Random random = new Random();
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            indexes.add(random.nextInt(maxIndex));
        }
        return indexes;
    }

    private static void printAverageTimeAndOperations(List<Long> times, List<Integer> operations) {
        long totalOperations = 0;
        long totalNanoseconds = 0;
        for (int i = 0; i < times.size(); i++) {
            totalOperations += operations.get(i);
            totalNanoseconds += times.get(i);
        }
        long averageOperations = totalOperations / times.size();
        long averageNanoseconds = totalNanoseconds / times.size();
        System.out.println("Среднее время: " + averageNanoseconds + " нс");
        System.out.println("Среднее количество операций: " + averageOperations);
    }
}