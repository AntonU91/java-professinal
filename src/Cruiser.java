public class Cruiser {                                      // a i current i current
    private int a = 0;                                      // 

    public void foo() {                                     // 
        Runnable r = new LittleCruiser();                   // 
        new Thread(r).start();                      // 
        new Thread(r).start();                      // 
    } // 4 1 2 2

    public static void main(String arg[]) {                // 
        Cruiser c = new Cruiser();                         // 
        c.foo(); // 4
    } // 6 3 6 6

    public class LittleCruiser implements Runnable {       // 
        public void run() {
            int current = 0;
            for (int i = 0; i < 4; i++) {
                current = a; // t2 stopped
                System.out.print(current + ", ");       // t1 stopped
                a = current + 2;
            }
        }
    }
}

// class ThreadA {
//     public static void main(String[] args) {        ThreadB b = new ThreadB();
//         b.start();

//         synchronized (b) {
//             try {
//                 System.out.println("Waiting for b to complete...");
//                 b.wait();
//             } catch (InterruptedException e) {
//             }
//             System.out.println("Total is: " + b.total);
//         }
//     }
// }

// class ThreadB extends Thread {
//     int total;

//     public void run() {
//    synchronized (this) {
//             for (int i = 0; i < 100; i++) {
//                 total += i;
//             }
//             notify();
//         }
//         Thread.currentThread().getId();
//     }
    
// }
