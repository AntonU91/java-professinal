package topic5_Generic.hometask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Dispatcher {
    public static void main(String[] args) {

        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("Murka", 5.4, 6));
        animals.add(new Animal("Pushok", 20, 2));
        animals.add(new Animal("Pushok", 20, 2));

        Animal[] animalArr = { new Animal("Murka", 5.4, 6),
                new Animal("Pushok", 20, 2),

                new Animal("Buryonka", 120, 10) };

        Dog[] dogArr = { new Dog("Lucky", 8, 4, 8),
                new Dog("Pushok", 20, 2, 10) };

        // HashMap<Animal, Integer> resultMap =
        // Controller.createMapwithMatchesElemnts(animals,
        // animalArr);
        // for (Entry<Animal,Integer> entrySet : resultMap.entrySet()) {
        // System.out.println(entrySet.getKey() + " - "+ entrySet.getValue());
        // }
        // Controller.addToListUnmatchedElements(animals, animalArr);

        // animals.forEach(System.out::println);

        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Pushok", 20, 2, 5));
        dogs.add(new Dog("Sharik", 12, 5, 7));

        HashMap<Animal, Integer> resultMap2 = Controller.createMapwithMatchesElemnts(dogs, animalArr);
        for (Entry<Animal, Integer> entrySet : resultMap2.entrySet()) {
            System.out.println(entrySet.getKey() + " - " + entrySet.getValue());
        }

        List<Poodle> poodles = new ArrayList<>();
        poodles.add(new Poodle("Lucky", 8, 4, 8));
        poodles.add(new Poodle("Kelli", 5, 12, 7));

        Controller.addToListUnmatchedElements(animals, animalArr);

    }

}

class Controller {
    public static <T> HashMap<T, Integer> createMapwithMatchesElemnts(List<? extends T> list,
            T[] arr) {

        HashMap<T, Integer> hMap = new HashMap<>();

        List<T> secondList = new ArrayList<>(Arrays.asList(arr));
        secondList.retainAll(list);
        for (T temp : secondList) {
            int matchesCount = 0;
            for (T temp2 : list) {
                if (temp.equals(temp2)) {
                    matchesCount++;
                }
            }
            if (matchesCount > 0) {
                hMap.put(temp, matchesCount);
            }
        }
        return hMap;
    }

    public static <T extends Animal> void addToListUnmatchedElements(List<? super Animal> list, T[] arr) {
        List<T> secondList = new ArrayList<>(Arrays.asList(arr));
        secondList.removeAll(list);
        list.addAll(secondList);
    }

}

class Animal {
    private String name;
    private double weight;
    private int age;

    public Animal(String name, double weight, int age) {
        this.name = name;
        this.weight = weight;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(weight);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + age;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        Animal other = (Animal) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
            return false;
        if (age != other.age)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Animal [name=" + name + ", weight=" + weight + ", age=" + age + "]";
    }

}

class Dog extends Animal {
    private int tailLength;

    public Dog(String name, double weight, int age, int tailLength) {
        super(name, weight, age);
        this.tailLength = tailLength;

    }

    public int getTailLength() {
        return tailLength;
    }

    public void setTailLength(int tailLength) {
        this.tailLength = tailLength;
    }

}

class Poodle extends Dog {
    public Poodle(String name, double weight, int age, int tailLength) {
        super(name, weight, age, tailLength);

    }

}
