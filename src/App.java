import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    private static double sum = 0;

    public static void main(String[] args) throws InterruptedException {

        String s = "sdfsadda,";
        String str = s.replaceAll("\\b(\\w+)(\\w)\\b(\\W)?",
                String.join("", "$1").toUpperCase() + "edwewe");

        System.out.println(str);
    }

    private static synchronized void addToSum(double value) {
        sum += value;
    }

    static class FileSum implements Runnable {
        private String filename;

        public FileSum(String filename) {
            this.filename = filename;
        }

        public void run() {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(filename));
                String line;
                while ((line = br.readLine()) != null) {
                    double value = Double.parseDouble(line);
                    addToSum(value);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
