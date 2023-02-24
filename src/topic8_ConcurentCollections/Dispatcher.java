package topic8_ConcurentCollections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * Із використанням потокобезпечних колекцій пакету java.util.concurrent створити синхронну багатопотокову систему 
 * для формування з трьох текстових файлів колекції з кількістю повторень кожного слова 
 * ДОВЖИНОЮ БІЛЬШЕ ОДНІЄЇ БУКВИ, В ЯКИХ СПІВПАДАЮТЬ ПЕРША ТА ОСТАННЯ БУКВИ. 
 * НЕ ВИКОРИСТОВУВАТИ проміжне створення колекцій для прочитаного тексту.
 */
public class Dispatcher {
    Map<String, Integer> resultList = new HashMap<>();

    public static void main(String[] args) {

        File file1 = new File("src/topic8_ConcurentCollections/f1");
        File file2 =  new File("src/topic8_ConcurentCollections/f2");
        File file3 =  new File("src/topic8_ConcurentCollections/f3");

        Thread t1 = new Thread(new TextHandler(file1));
        Thread t2 = new Thread(new TextHandler(file2));
        Thread t3 =   new Thread(new TextHandler(file3));

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
        
 System.out.println( TextHandler.resultList);
        
    }

}


class TextHandler implements Runnable {
    private File file;
    static Map<String, Integer> resultList = Collections.synchronizedMap(new HashMap<>());

    public TextHandler(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String retrievedLine;
            while ((retrievedLine = bf.readLine()) != null) {
                String [] words = retrievedLine.replaceAll("\\b(\\w+)[\\.,\\?!:-]*\\s", "$1 ").split("\\s+");
                for (String temp : words) {
                    if (temp.length() > 1 & temp.substring(0, 1)
                            .equalsIgnoreCase(temp.substring(temp.length() - 1))) {
                      resultList.compute(temp, (key, value) -> value == null ? 1 : ++value);
                    }
                }
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
