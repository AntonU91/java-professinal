package final_thread_task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FamilyListGenerator {

    public static void main(String[] args) {
        List<Family> familyList =  createFamily(40);
        familyList.forEach(System.out::println);
    }

   static   List<Family> createFamily(int peoleCount) {
        List<Family> familyList = new ArrayList<>();
        Random random = new Random();
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
        String [] city =  {"Kalush","Kosiv", "Galych", "Kolomiya"};
        StringBuilder stringBuilder = new StringBuilder();
        int nameLength = 2;
        while (peoleCount > 0) {
            Family family = new Family();
            int index;
            for (int i = 0; i < nameLength; i++) {
                index = random.nextInt(CHARACTERS.length());
                stringBuilder.append(CHARACTERS.charAt(index));
            }
            family.setName(stringBuilder.toString());
            stringBuilder.setLength(0);
            index =random.nextInt(city.length);
            family.setTravelTo(city[index]);
            index = random.nextInt(4)+1;
            family.setCount(index);
            peoleCount -=index;
            familyList.add(family);
        }
        return familyList;
    }
}
