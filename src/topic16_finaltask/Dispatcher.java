package topic16_finaltask;

/*
Із комплексним використанням технологій багатопотоковості (threads) та паралельних потоків фреймворку API Streams створити максимально паралелізовану синхронну
систему для визначення середньої довжини всіх слів в N текстових файлах.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Dispatcher {

    public static void main(String[] args) {
        File[] filesToRead = new File[]{new File("src/topic16_finaltask/first_file.txt"), new File("src/topic16_finaltask/second_file.txt"), new File("src/topic16_finaltask/third_file.txt")};
        ExecutorService executorService = Executors.newFixedThreadPool(filesToRead.length);
        List<FileHandler> fileHandlerList = new ArrayList<>();
        Arrays.stream(filesToRead).forEach(file -> fileHandlerList.add(new FileHandler(file)));
        List<Future<Double>> futures = new ArrayList<>();

        long before = System.currentTimeMillis();
        try {
            futures = executorService.invokeAll(fileHandlerList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        double totalAverageLetterInText = futures.stream().map(future -> {
            Double result = 0.0;
            try {
                result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return result;
        }).reduce(0.0, (a, b) -> a + b / filesToRead.length);
        long after = System.currentTimeMillis();

        System.out.println(after - before);
        System.out.println(totalAverageLetterInText);
    }

}


class FileHandler implements Callable<Double> {
    BufferedReader bufferedReader;
    private final TextHandler textHandler = new TextHandler();

    public FileHandler(File file) {
        try {
            this.bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double call() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        String retrievedStr = "";
        try {
            while ((retrievedStr = bufferedReader.readLine()) != null) {
                stringBuilder.append(retrievedStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuilder.toString().chars().parallel() /// parallel stream  loses to sequential approximately by 60-80 ms
                .mapToObj(el -> (char) el).forEach(textHandler::countSymbolsBySpecifiedConditions);
        return textHandler.getTotalCountLetter() * 1.0 / textHandler.getWordsCount();
    }
}

class TextHandler {
    private Integer totalCountLetter = 0;
    private Integer wordsCount = 1;
    private char lastChar = ' ';

    public  void countSymbolsBySpecifiedConditions(char ch) { // Specifying synchronization in the method declaration does not work properly
        // but synchronized blocks instead make the calculations correct
        if (ch == ' ' && lastChar != ' ')
            synchronized (this) {
                wordsCount++;
            }
        if (Character.isLetter(ch))
            synchronized (this) {
                totalCountLetter++;
                lastChar = ch;
            }
    }

    public Integer getTotalCountLetter() {
        return totalCountLetter;
    }


    public Integer getWordsCount() {
        return wordsCount;
    }
}
