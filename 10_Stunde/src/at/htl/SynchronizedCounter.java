package at.htl;

public class SynchronizedCounter extends Counter {

    private Object mySpecialLockObject = new Object();
    private Object anotherLockObject = new Object();

    @Override
    public /*synchronized*/ void increment() {
        synchronized (mySpecialLockObject) {
            value++;
        }
    }
}
