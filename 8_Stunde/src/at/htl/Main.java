package at.htl;

public class Main {
    public static void main(String[] args) {
        Thread t1 = new CounterThread("CounterThread-1");
        Thread t2 = new CounterThread("CounterThread-2");
        Thread t3 = new ABCThread();
        Thread t4 = new ABCThread();
        Thread t5 = new EndlessThread();

        //t1.start();
        //t2.start();
        //t3.start();
        //t4.start();
        //t5.start();

        for (int i = 0; i < 12; i++) {
            Thread schleife = new EndlessThread();
            schleife.start();
        }
    }
}
