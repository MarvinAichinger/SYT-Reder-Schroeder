package at.htl;

public class CounterThread extends Thread {

    private CounterInterface counter;
    private int maxCount;

    public CounterThread(CounterInterface counter, int maxCount) {
        this.counter = counter;
        this.maxCount = maxCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < maxCount; i++) {
            counter.increment();
        }
    }

}
