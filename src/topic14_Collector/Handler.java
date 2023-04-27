package topic14_Collector;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static jdk.nashorn.internal.objects.Global.print;

public class Handler {

    public static void main(String[] args) {
        //  - в колекції цілих чисел поміняти місцями максимум та мінімум;
        List<Integer> initialList = new ArrayList<>(Arrays.asList(20, -1, 20, 9, -50, 0, 9, 20, 8, 11, -50));
        IntSummaryStatistics summaryStatistics = initialList.stream().mapToInt(Integer::intValue).summaryStatistics();

        int max = summaryStatistics.getMin();
        int min = summaryStatistics.getMax();

        List<Integer> modifiedList = initialList.stream().map(integer -> {
            if (integer == max) return min;
            else if (integer == min) return max;
            else return integer;
        }).collect(Collectors.toList());

        System.out.println(modifiedList);

        // визначити середнє арифметичне елементів колекції цілих чисел
        // та сформувати вихідну колекцію з елементів, що є більшими за середнє арифметичне;

        IntSummaryStatistics summaryStatistics2 = initialList.stream().mapToInt(Integer::intValue).summaryStatistics();
        double average = summaryStatistics2.getAverage();
        List<Integer> modifiedList2 = initialList.stream().filter(integer -> integer > average).collect(Collectors.toList());
        System.out.println(modifiedList2);

        // в кожному реченні тексту без використання попереднього розбиття на речення визначити різницю між кількістю приголосних та голосних букв
        // і сформувати відповідний Map (key: номер речення, value: визначена різниця між кількістю приголосних та голосних букв).
        String text = "Lsqss iii.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam venenatis tempus tincidunt. " +
                "Aenean non ex fringilla, dapibus eros quis, accumsan enim. Donec egestas sed nulla pretium pulvinar. " +
                "Mauris dignissim ligula id fermentum condimentum. Aliquam aliquam tellus nibh, ut vulputate est faucibus nec." +
                " Etiam ornare scelerisque dolor et varius.";

        AtomicInteger sentenceInOrder = new AtomicInteger(1);
        AtomicInteger divisionCounter = new AtomicInteger();
        Map<Integer, Integer> result = text.chars()
                .mapToObj(el -> (char) el)
                .collect(Collectors.toMap(ch -> {
                            if (ch == '.') {
                                sentenceInOrder.getAndIncrement();
                                divisionCounter.set(0);
                            }
                            return sentenceInOrder.get();
                        },
                        ch -> {
                            if (ch != '.' && ch != ' ') {
                                if (ch.toString().matches("[aeiouAEIOU]")) {
                                    divisionCounter.getAndDecrement();
                                } else if (ch.toString().matches("[b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z]")) {
                                    divisionCounter.getAndIncrement();
                                }
                            }
                            return divisionCounter.get();
                        },
                        (k, v) -> v));

        result.remove(sentenceInOrder.get());
        System.out.println(result);

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("a", 3);
        System.out.println(map);
    }
}
