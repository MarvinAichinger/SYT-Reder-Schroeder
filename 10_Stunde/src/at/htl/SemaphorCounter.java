package at.htl;

import java.util.concurrent.Semaphore;

public class SemaphorCounter extends Counter {

    private Semaphore semaphore = new Semaphore(1, false); //fair viel langsamer

    @Override
    public void increment() {
        if(semaphore.hasQueuedThreads()) {
            System.out.println(semaphore.toString());
        }
        semaphore.acquireUninterruptibly();
        super.increment();
        semaphore.release();
    }

}
