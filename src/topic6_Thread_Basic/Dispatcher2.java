package topic6_Thread_Basic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public class Dispatcher2 {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService eService = Executors.newCachedThreadPool();

        File file1 = new File("src/topic6_Thread_Basic/first_file.txt");
        File file2 = new File("src/topic6_Thread_Basic/second_file.txt");
        File file3 = new File("src/topic6_Thread_Basic/third_file.txt");

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);
        ////// USING THREADS
        long before = System.currentTimeMillis();

        for (File temp : files) {

            Callable<Integer> callable1 = new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                 return countTextDilimeters(temp);
                }
            };
            Future<Integer> future = eService.submit(callable1);
        }
        eService.shutdown();
      
        long after = System.currentTimeMillis();
        System.out.println(System.out.format("Execution task with using threads takes %dms ",
                after - before));
        System.out.println();

        /////// USING one thread - main
        long before2 = System.currentTimeMillis();
        List<Integer> resultList = new ArrayList<>();
        for (File temp : files) {
            resultList.add(countTextDilimeters(temp));
        }
        long after2 = System.currentTimeMillis();
        System.out.println(System.out.format("Execution task with using one thread takes %dms ",
                after2 - before2));

    }

    private static Integer countTextDilimeters(File file) {
        Pattern pattern = Pattern.compile("\\w+[\\?\\.\\-,:!;]");
        int count = 0;
        try (Scanner scanner = new Scanner(file)) {
            String retrievedStr = "";
            while (scanner.hasNext()) {
                retrievedStr = scanner.next();
                if (retrievedStr.matches(pattern.pattern())) {
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }
}
