package topic1_Serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Dispatcher {
    public static void main(String[] args) {
        File file = new File("/Users/toxauz/Desktop/Visual Studio Java Proj/java-professinal/src/topic1_Serialization/object_store_file.txt");
        Engine e = new Engine("MOTORSICH", 200);
        Car car = new Car(e, "ZAZ", 2000); 

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(car);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Car car2 = (Car) ois.readObject();
            System.out.println(car2);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
       
    }
    
}

class Car implements Serializable {
    transient Engine e;
    String model;
    int price;

    
    public Car(Engine e, String model, int price) {
        this.e = e;
        this.model = model;
        this.price = price;
    }
    private void writeObject(ObjectOutputStream oos){
        
            try {
                oos.defaultWriteObject();
                oos.writeInt(e.horsePower);
                oos.writeObject(e.producer);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    private void readObject(ObjectInputStream oi) {
      
            try {
                oi.defaultReadObject();
                int horsePower =    oi.readInt();
                String producer   =  (String) oi.readObject();
                this.e =new Engine(producer, horsePower);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
   
    }


    @Override
    public String toString() {
        return "Car [ model=" + model + ", price=" + price + ",e.producer= "+e.producer +", e.power = " + e.horsePower+ " ]";
    }
    
} 