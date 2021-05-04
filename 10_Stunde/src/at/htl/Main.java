package at.htl;

public class Main {

    public static final int NR_THREADS = 5;
    public static final int MAX_COUNT = 1_000_000;


    public static void main(String[] args) {

        testCounterThreads("Unsynchronised Threads", new Counter());
        testCounterThreads("Unsynchronised mit BusyWaiting Threads", new BusyWaitingCounter());
        testCounterThreads("Synchronized mit keyword synchronized", new SynchronizedCounter());
        testCounterThreads("Atomic Counter", new AtomicCounter());
        testCounterThreads("Semaphore Counter", new SemaphorCounter());

    }

    private static void testCounterThreads(String title, CounterInterface counter) {
        CounterThread[] threads = new CounterThread[NR_THREADS];

        long start = System.currentTimeMillis();
        for (int i = 0; i < NR_THREADS; i++) {
            threads[i] = new CounterThread(counter, MAX_COUNT);
            threads[i].start();
        }

        try {
            for (int i = 0; i < NR_THREADS; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(title + " fertig nach " + (end - start) + " ms. Wert: " + counter.getValue());
    }

}
