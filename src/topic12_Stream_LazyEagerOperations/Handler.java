package topic12_Stream_LazyEagerOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Програмнi завдання.
На основі методів інтерфейсу Stream виконати наступні дії:
- в тексті реалізувати початок кожного слова з прописної (великої) букви;
- вивести всi речення тексту за зростанням кількості слів, припустимо, що всі речення завершуються тільки крапкою;
- колекцію цілих чисел поділити на колекції з додатніх та від’ємних елементів.
 */
public class Handler {
    public static void main(String[] args) {
        //  - в тексті реалізувати початок кожного слова з прописної (великої) букви;
        String text = "just simple text for modifying, nothing interesting";
        String result = Pattern.compile("\\s+").splitAsStream(text)
                .map(s -> s = s.substring(0, 1).toUpperCase().concat(s.substring(1)))
                .collect(Collectors.joining(" "));
        System.out.println(result);

        //- вивести всi речення тексту за зростанням кількості слів, припустимо, що всі речення завершуються тільки крапкою;

        String text2 = "Just simple text for modifying, nothing interesting. Nevermind. " +
                "The major goal is to fix knowledge of current topic. Perhaps, you will not able to complete this task first time.";
        String result2 = Pattern.compile("\\.").splitAsStream(text2)
                .sorted(Comparator.comparing(String::length))
                .collect(Collectors.joining("."));
        System.out.println(result2);

        // - колекцію цілих чисел поділити на колекції з додатніх та від’ємних елементів.
        List<Integer> digitsList = new ArrayList<>(Arrays.asList(23, -1, 35, 0, 5, -45, 69, -6, -23));
        List<Integer> positiveDigitsList = digitsList.stream().filter((s) -> s > -1).collect(Collectors.toList());
        positiveDigitsList.forEach(System.out::println);
        List<Integer> negativeDigitsList = digitsList.stream().filter((s) -> s < 0).collect(Collectors.toList());
        negativeDigitsList.forEach(System.out::println);



    }
}

