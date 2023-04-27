package topic_10_Wait_notify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
 * Програмнe завдання. Спроектувати двопотокову систему обробки текстових файлів наступним чином: 
 * перший потік визначає кількість пробілів у файлі, якщо кількість пробілів 
 * є парною - другий потік робить у файлі прописними (великими) перші букви всіх слів,
 *  якщо непарною – останні букви. Забезпечити наступність обробки файлів потоками – обробка другим
 *  потоком поточного файлу здійснюється під час обробки першим потоком наступних файлів.
Порівняти ефективність роботи розробленої системи із класичною багатопотоковою системою, 
де кожний потік обробляє повністю один файл, а також у випадках обробки файлів 
приблизно однакового обсягу та з критично різними обсягами.
 */

public class Handler {
    public static void main(String[] args) {
        long before = System.currentTimeMillis();
        File file = new File("src/topic6_Thread_Basic/first_file.txt");
        TextInFileHandler textInFileHandler = new TextInFileHandler(file);

        SpaceInTextCounter spaceInTextCounter = new SpaceInTextCounter(textInFileHandler);
        LetterInTextChanger letterInTextChanger = new LetterInTextChanger(textInFileHandler);

        long after = System.currentTimeMillis();
        System.out.println(after - before);

    }
}

class SpaceInTextCounter implements Runnable {
    TextInFileHandler tHandler;

    public SpaceInTextCounter(TextInFileHandler tHandler) {
        this.tHandler = tHandler;
        new Thread(this).start();
    }

    @Override
    public void run() {
        {
            tHandler.countSpacesInText();
        }

    }

}

class LetterInTextChanger implements Runnable {
    TextInFileHandler tHandler;

    public LetterInTextChanger(TextInFileHandler tHandler) {
        this.tHandler = tHandler;
        new Thread(this).start();
    }

    @Override
    public void run() {

        tHandler.changeSpecigiedLettersInText();

    }
}

class TextInFileHandler {
    File file;
    boolean flag;
    long spacesCount;
   

    public TextInFileHandler(File file) {
        this.file = file;
    }

    synchronized void countSpacesInText() {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                scanner.next();
                ++spacesCount;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        flag = true;
        notify();
    }

    synchronized void changeSpecigiedLettersInText() {
        while (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter bufWrite = new BufferedWriter(new FileWriter(file, true));
                Scanner scanner = new Scanner(file)) {
            if (spacesCount % 2 == 0) {
                while (scanner.hasNext()) {
                    String str = scanner.next();//
                 str = str.replace(str.substring(0, 1), 
                    str.substring(0, 1).toUpperCase());
                    bufWrite.write(str + " ");
                }
            } else {
                while (scanner.hasNext()) {
                    String str = scanner.next();
                       str = str.replace(str.substring(str.length()-1), 
                       str.substring(str.length()-1).toUpperCase());
                       bufWrite.write(str + " ");
                }
                bufWrite.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        flag = false;
        notify();
    }
}
