package topic7_synchronized;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*1. Із використанням ключового слова synchronized створити синхронну багато-потокову систему 
для визначення загальної суми всіх наявних цілих та дробових чисел в трьох текстових файлах.
НЕ УТВОРЮВАТИ суму всіх чисел з кожного файлу окремо, потім додаючи три суми, 
результат треба утворювати послідовно - по мірі зчитування чисел з файлів. 
НЕ ВИКОРИСТОВУВАТИ проміжне створення колекцій для прочитаного тексту і не перетворювати весь файл у текст.
*/

public class Dispatcher {

    public static void main(String[] args) {

        TextDigitCounter tCounter = new TextDigitCounter();
        try (BufferedReader bReader1 = new BufferedReader(new FileReader("src/text1.txt"));
                BufferedReader bReader2 = new BufferedReader(new FileReader("src/text2.txt"));
                BufferedReader bReader3 = new BufferedReader(new FileReader("src/text3.txt"));) {

            Runnable r1 = () -> {
                tCounter.countSumOfTextDigit(bReader1);
            };

            Runnable r2 = () -> {
                tCounter.countSumOfTextDigit(bReader2);
            };
            Runnable r3 = () -> {
                tCounter.countSumOfTextDigit(bReader3);
            };

            Thread thread1 = new Thread(r1);
            Thread thread2 = new Thread(r2);
            Thread thread3 = new Thread(r3);

            thread1.start();
            thread2.start();
            thread3.start();
            
            thread1.join();
            thread2.join();
            thread3.join();
            System.out.println(tCounter.countValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}

class TextDigitCounter {
    public static final Pattern PATTERN = Pattern.compile("([1-9]+[.,]\\d+)");
    double countValue;

    public void countSumOfTextDigit(BufferedReader bReader) {
        synchronized (this) {
            Matcher matcher = null;
            String retrievedLine = null;
            try {
                while ((retrievedLine = bReader.readLine()) != null) {
                    matcher = PATTERN.matcher(retrievedLine);
                    while (matcher.find()) {
                        countValue += Double.parseDouble(matcher.group());
                    }
                }
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
 * 2. Аналогічну систему створити із використанням ресурсів пакету
 * java.util.concurrent.atomic.
 */
class Dispatcher2 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        TextDigitCounter2 tCounter2 = new TextDigitCounter2();
        try (BufferedReader bReader1 = new BufferedReader(new FileReader("src/text1.txt"));
                BufferedReader bReader2 = new BufferedReader(new FileReader("src/text2.txt"));
                BufferedReader bReader3 = new BufferedReader(new FileReader("src/text3.txt"));) {

            Runnable r1 = () -> {
                tCounter2.countSumOfTextDigit(bReader1);
            };

            Runnable r2 = () -> {
                tCounter2.countSumOfTextDigit(bReader2);
            };
            Runnable r3 = () -> {
                tCounter2.countSumOfTextDigit(bReader3);
            };

            Thread thread1 = new Thread(r1);
            Thread thread2 = new Thread(r2);
            Thread thread3 = new Thread(r3);

            thread1.start();
            thread2.start();
            thread3.start();
            try {
                thread1.join();
                thread2.join();
                thread3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(tCounter2.countValue.get());

        }

    }
}

class TextDigitCounter2 {
    public static final Pattern PATTERN = Pattern.compile("([1-9]+[.,]\\d+)");
    DoubleAccumulator countValue = new DoubleAccumulator((x1, x2) -> {
        return x1 + x2;
    }, 0);

    public void countSumOfTextDigit(BufferedReader bReader) {
        Matcher matcher = null;
        String retrievedLine = null;
        try {
            while ((retrievedLine = bReader.readLine()) != null) {
                matcher = PATTERN.matcher(retrievedLine);
                while (matcher.find()) {
                    countValue.accumulate(Double.parseDouble(matcher.group()));
                }
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }

    }
}
