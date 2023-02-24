package topic2_Parsing;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

/*За допомогою класів Pattern, Matcher / Scanner (за вибором слухача)
в заданому текстовому файлі видалити всі слова заданої довжини, 
що починаються на приголосну букву. Опрацьовані фрагменти тексту заносити до нового текстового файлу безпосередньо
після опрацювання без утворення буферних колекцій або без перетворення всього файлу у текст. */

public class Dispatcher {

    public static void main(String[] args) {

        String origineText = "Lorem ipsum dolor sit amet lorem, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n"
                +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n"
                +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\n"
                +
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        File fileWithModifiedText = new File("src/topic2_Parsing/modified_text.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileWithModifiedText));
                Scanner scanner = new Scanner(origineText);) {
            Pattern pattern = Pattern.compile("\\b[^AEIOUYaeiouy]+\\w{4}\\b");
            String stringForRecord = "";
            while (scanner.hasNext()) {
                stringForRecord = scanner.next();
                if (!stringForRecord.matches(pattern.pattern())) {
                    bw.write(stringForRecord + " ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
