package topic7_synchronized.correctSolutionForLessonTask;

public class Solution {
    public static void main(String[] args) throws InterruptedException {
        Ticket[] tickets = {new Ticket(1, false), new Ticket(2, false), new Ticket(3, false)
                , new Ticket(4, false), new Ticket(5, false)};

        Cashier cashier = new Cashier(1, tickets);
        Cashier cashier2 = new Cashier(2, tickets);
        Cashier cashier3 = new Cashier(3, tickets);
        cashier.start();
        cashier2.start();
        cashier3.start();


    }
}

class Ticket {
    int place;
    boolean isBought;

    public Ticket(int place, boolean isBought) {
        this.place = place;
        this.isBought = isBought;
    }

    public synchronized void buy(int cashierId)  { /// квиток купується (passive voice)
        if (!this.isBought) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.isBought = true;
            System.out.println(String.format("Ticket with seat %d was bought by cahier %d", this.place, cashierId));
        }
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
        for (Ticket ticket : tickets) {
            ticket.buy(this.id);
        }
    }

}


