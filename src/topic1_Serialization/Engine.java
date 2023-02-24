package topic1_Serialization;

public class Engine {
    String producer;
    int horsePower;
    
    
    public Engine(String producer, int horsePower) {
        this.producer = producer;
        this.horsePower = horsePower;
    }


    @Override
    public String toString() {
        return "Engine [producer=" + producer + ", horsePower=" + horsePower + "]";
    }

}
