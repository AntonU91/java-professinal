package topic7_synchronized;

public class Lesson {
    public static void main(String[] args) throws InterruptedException {

        Ticket[] tickets = {new Ticket(1, false), new Ticket(2, false), new Ticket(3, false)
                , new Ticket(4, false), new Ticket(5, false)};

        Cashier cashier = new Cashier(1, tickets);
        Cashier cashier2 = new Cashier(2, tickets);
        Cashier cashier3 = new Cashier(3, tickets);
        cashier.start();
        cashier2.start();
        cashier3.start();
        cashier.join();
        cashier2.join();
        cashier3.join();

    }
}

class Ticket {
    int place;
    boolean isBought;

    public Ticket(int place, boolean isBought) {
        this.place = place;
        this.isBought = isBought;
    }
}

class Cashier extends Thread {
    int id;
    Ticket[] tickets;

    public Cashier(int id, Ticket[] tickets) {
        this.id = id;
        this.tickets = tickets;
    }

    @Override
    public void run() {
        buy();
    }

    public synchronized void  buy() {  /// даний метод не буде працювати коректно навіть якщо ми поставимо йому synchronized. Дай відповідь на питання "Чому?"
        for (Ticket ticket : tickets) { // тут  поток може зупинитися і це може призвести до  того що один квиток продасться два рази!!
            if (!ticket.isBought) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.isBought = true;
                System.out.println("Cashier with id " + id + " sold a  ticket  for seat " + ticket.place);
            }
        }
    }
}

