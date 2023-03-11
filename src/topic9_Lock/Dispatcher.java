package topic9_Lock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dispatcher {

    public static void main(String[] args) {
        File file1 = new File("src/text1.txt");
        File file2 = new File("src/text2.txt");
        File file3 = new File("src/text3.txt");

        Thread t1 = new Thread(new TextHandler(file1, "ONE"));
        Thread t2 = new Thread(new TextHandler(file2, "TWO"));
        Thread t3 = new Thread(new TextHandler(file3, "THREE"));

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();

    }

}

class Counter {
    static double count;
}

class TextHandler implements Runnable {
    public static final Pattern PATTERN = Pattern.compile("([1-9]+[.,]\\d+)");
    private File file;
    String title;
    private static double textDigitSum;
    Lock lock = new ReentrantLock();

    public TextHandler(File file, String title) {
        this.file = file;
        this.title = title;
    }

    @Override
    public void run() {
        try (BufferedReader bReader = new BufferedReader(new FileReader(file))) {
            Matcher matcher = null;
            String retrievedLine = null;
            double temp = 0;
            while ((retrievedLine = bReader.readLine()) != null) {
                matcher = PATTERN.matcher(retrievedLine);
                while (matcher.find()) {
                    temp = Double.parseDouble(matcher.group());
                    try {
                        while (!lock.tryLock()) {
                            System.out.println( Thread.currentThread().getName());
                        }
                       Counter.count += temp;
                    } finally {
                        lock.unlock();
                    }
                }
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    public File getFile() {
        return file;
    }

    public static double getTextDigitSum() {
        return textDigitSum;
    }
}
