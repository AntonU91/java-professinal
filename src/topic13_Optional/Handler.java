package topic13_Optional;

import java.util.*;
import java.util.function.DoubleConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Handler {
    /*
 Програмнi завдання.
На основі методів потокового зведення інтерфейсу Stream виконати дії:
- утворити третій контейнер ArrayList як конкатенацію двох масивів різної довжини;
- визначити середнє арифметичне елементів масиву цілих чисел та кількість елементів, що є більшими за середнє арифметичне;
- з колекції цілих чисел видалити дублікати максимума та мінімума.
     */
    public static void main(String[] args) {
        //- утворити третій контейнер ArrayList як конкатенацію двох масивів різної довжини;
        int[] arr1 = {234, 5, -1, 3};
        int[] arr2 = {1, 0, 34, -2, 3, 6, 12};
        List<Integer> integerList = Stream.concat(Arrays.stream(arr1).boxed(), Arrays.stream(arr2).boxed()).collect(Collectors.toCollection(() -> new ArrayList<>()));
        integerList.forEach(System.out::println);

        //- визначити середнє арифметичне елементів масиву цілих чисел та кількість елементів, що є більшими за середнє арифметичне;

        OptionalDouble average = Arrays.stream(arr2).average();
        average.ifPresent(value -> {
            System.out.println(value);
        });
        long count = Arrays.stream(arr2).filter(value -> value > average.getAsDouble()).count();
        System.out.println(count);

        //   - з колекції цілих чисел видалити дублікати максимума та мінімума.
        List<Integer> initialList = new ArrayList<>(Arrays.asList(20, -1, 20, 9, -50, 0, 9, 20, 8, 11, -50));
        Optional<Integer> min = initialList.stream().min(Integer::compare);
        Optional<Integer> maх = initialList.stream().max(Integer::compare);
        Predicate<Integer> predicate = val -> val.equals(min.get()) || val.equals(maх.get());
        List<Integer> listWithMaxAndMin = initialList.stream().filter(predicate).distinct().collect(Collectors.toList());
        initialList.removeIf(predicate);
        List<Integer> eventualList = Stream.concat(initialList.stream(), listWithMaxAndMin.stream()).collect(Collectors.toList());
        System.out.println("++++++++++++++++++++++++++++++++++=");
        eventualList.forEach(System.out::println);
    }

}
