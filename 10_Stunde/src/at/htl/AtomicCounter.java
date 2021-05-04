package at.htl;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements CounterInterface {

    private AtomicInteger counter = new AtomicInteger();

    @Override
    public void increment() {
        counter.incrementAndGet();
    }

    @Override
    public int getValue() {
        return counter.intValue();
    }
}
