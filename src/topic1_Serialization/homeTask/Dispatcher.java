package topic1_Serialization.homeTask;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*Дано наступних 7 класів:
- “Транспортний засіб” з полями:
- швидкість,
- рік виробництва,
- двигун,
- “Літак” (наслідує “Транспортний засіб” та реалізує Serializable) з полями:
- модель,
- дальність польоту,
- шассі,
- “Корабель” (наслідує “Транспортний засіб” і реалізує Externalizable) з полями:
- водотонажність,
- довжина,
- шлюпка,
- “Двигун” з полями:
- тип,
- потужність,
- “Шассі” з полями:
- колесо,
- кількість коліс,
- “Шлюпка” з полями:
- кількість пасажирів,
- матеріал,
- “Колесо” з полями:
- навантаження,
- діаметр. */

public class Dispatcher {
    public static void main(String[] args) {
        /*
         * 1) cтворити об’єкти класів літаків та кораблів, які зберегти у двох
         * відповідних ArrayLists, сортованих по роках виробництва,
         */
        Plane boeng = new Plane(1600, 2018, new Engine("SDF-123", 175000), "Boeng 700", 12000,
                new LandingGear(new Wheel(1500, 1500), 6));
        Plane ruslan = new Plane(1800, 2002, new Engine("UKR-9001", 250000), "Ruslan", 14000,
                new LandingGear(new Wheel(3000, 2000), 10));
        Plane airbus = new Plane(1500, 2001, new Engine("SF-234", 180000), "Airbus-1234", 13000,
                new LandingGear(new Wheel(1500, 1500), 6));
        List<Plane> planesList = new ArrayList<>();
        planesList.add(boeng);
        planesList.add(ruslan);
        planesList.add(airbus);
        planesList.sort(null);

        Ship symphonyOfTheSea = new Ship(200, 2018, new Engine("FEQ-1345656", 200000), 220021, 362,
                new Boat(20, "Metal"));
        Ship harmonyOfTheSea = new Ship(180, 2015, new Engine("FEQ-5631", 170000), 150256, 320, new Boat(20, "Metal"));
        Ship allure = new Ship(205, 2004, new Engine("KE-5631y", 190000), 150256, 350, new Boat(20, "Metal"));

        List<Ship> shipList = new ArrayList<>();
        shipList.add(symphonyOfTheSea);
        shipList.add(harmonyOfTheSea);
        shipList.add(allure);
        shipList.sort(null);

        /*
         * серіалізувати створені ArrayLists до відповідних файлів: PLANES за допомогою
         * інтерфейсу Serializable та SHIPS за допомогою інтерфейсу Externalizable,
         */
        File planes = new File(
                "/Users/toxauz/Desktop/Visual Studio Java Proj/java-professinal/src/topic1_Serialization/homeTask/PLANES.txt");

        File ships = new File(
                "/Users/toxauz/Desktop/Visual Studio Java Proj/java-professinal/src/topic1_Serialization/homeTask/SHIPS.txt");

        try {
            planes.createNewFile();
            ships.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /// PLANES
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(planes))) {
            oos.writeObject(planesList);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(planes))) {
            List<Plane> retriviedPlaneList = (ArrayList<Plane>) ois.readObject();
          retriviedPlaneList.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        /// SHIPS
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ships))) {
            oos.writeObject(shipList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ships))) {
             List<Ship>retrievedShip = (ArrayList<Ship>) ois.readObject();
            System.out.println(retrievedShip);
        } catch (IOException e) {
          
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

class Vehicle implements Comparable<Vehicle>, Serializable {
    private int speed;
    private int yearProducing;
    transient private Engine engine;

    public Vehicle(int speed, int yearProducing, Engine engine) {
        this.speed = speed;
        this.yearProducing = yearProducing;
        this.engine = engine;
    }

    public Vehicle() {
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getYearProducing() {
        return yearProducing;
    }

    public void setYearProducing(int yearProducing) {
        this.yearProducing = yearProducing;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public int compareTo(Vehicle o) {
        return getYearProducing() - o.getYearProducing();
    }

    @Override
    public String toString() {
        return "Vehicle [speed=" + speed + ", yearProducing=" + yearProducing + ", engine type=" + engine.type + "]";
    }

}

class Plane extends Vehicle {
    String model;
    int flightRange;
    transient LandingGear landingGear;

    public Plane() {

    }

    public Plane(int speed, int yearProducing, Engine engine, String model, int flightRange,
            LandingGear landingGear) {
        super(speed, yearProducing, engine);
        this.model = model;
        this.flightRange = flightRange;
        this.landingGear = landingGear;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getFlightRange() {
        return flightRange;
    }

    public void setFlightRange(int flightRange) {
        this.flightRange = flightRange;
    }

    public LandingGear getLandingGear() {
        return landingGear;
    }

    public void setLandingGear(LandingGear landingGear) {
        this.landingGear = landingGear;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {

        out.defaultWriteObject();
        out.writeObject(this.getEngine().type);
        out.writeInt(this.getEngine().power);
        out.writeInt(landingGear.getWheel().getLoading());
        out.writeDouble(landingGear.getWheel().getDiameter());
        out.writeInt(landingGear.getWheelsCount());

    }

    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        this.setEngine(new Engine((String) in.readObject(), in.readInt()));
        LandingGear landingGear = new LandingGear(new Wheel(in.readInt(), in.readDouble()), in.readInt());
        this.setLandingGear(landingGear);
    }

    @Override
    public String toString() {
        return "Plane [model=" + model + ", flightRange=" + flightRange + ", landingGear=" + landingGear + "]";
    }

}

class Ship extends Vehicle implements Externalizable {
    int waterDisplacement;
    int length;
    transient Boat boat;

    public Ship() {
    }

    public Ship(int speed, int yearProducing, Engine engine, int waterDisplacement, int length, Boat boat) {
        super(speed, yearProducing, engine);
        this.waterDisplacement = waterDisplacement;
        this.length = length;
        this.boat = boat;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.getSpeed());
        out.writeInt(this.getYearProducing());
        out.writeObject(this.getEngine().type);
        out.writeInt(this.getEngine().power);
        out.writeInt(this.getBoat().passangersCount);
        out.writeObject(this.getBoat().material);
        out.writeInt(waterDisplacement);
        out.writeInt(length);
       
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setSpeed(in.readInt());
        this.setYearProducing(in.readInt());
        this.setEngine(new Engine((String) in.readObject(), in.readInt()));
        this.setBoat( new Boat(in.readInt(), (String)in.readObject()));
        this.setWaterDisplacement(in.readInt());
        this.setLength(in.readInt());
    }

    public int getWaterDisplacement() {
        return waterDisplacement;
    }

    public void setWaterDisplacement(int waterDisplacement) {
        this.waterDisplacement = waterDisplacement;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    @Override
    public String toString() {
        return "Ship [waterDisplacement=" + waterDisplacement + ", length=" + length + ", boat=" + boat + "]";
    }

}

class Engine {
    String type;
    int power;

    public Engine() {
    }

    public Engine(String type, int power) {
        this.type = type;
        this.power = power;
    }
}

class LandingGear {
    private Wheel wheel;
    private int wheelsCount;

    public LandingGear(Wheel wheel, int wheelsCount) {
        this.wheel = wheel;
        this.wheelsCount = wheelsCount;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public int getWheelsCount() {
        return wheelsCount;
    }

    public void setWheelsCount(int wheelsCount) {
        this.wheelsCount = wheelsCount;
    }

    @Override
    public String toString() {
        return "LandingGear [wheel=" + wheel + ", wheelsCount=" + wheelsCount + "]";
    }

}

class Wheel {
    private int loading;
    private double diameter;

    public Wheel(int loading, double diameter) {
        this.loading = loading;
        this.diameter = diameter;
    }

    public int getLoading() {
        return loading;
    }

    public void setLoading(int loading) {
        this.loading = loading;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    @Override
    public String toString() {
        return "Wheel [loading=" + loading + ", diameter=" + diameter + "]";
    }

}

class Boat {
    int passangersCount;
    String material;

    public Boat() {
    }

    public Boat(int passangersCount, String material) {
        this.passangersCount = passangersCount;
        this.material = material;
    }

    @Override
    public String toString() {
        return "Boat [passangersCount=" + passangersCount + ", material=" + material + "]";
    }

}
