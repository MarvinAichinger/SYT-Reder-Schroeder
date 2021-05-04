package at.htl;

public class CounterThread extends Thread{

    public CounterThread(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        System.out.println("CounterThread " + this.getName() + " is starting...");

        for (int i = 1; i <= 100; i++) {
            System.out.print(i + " ");
            try {
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println();
    }
}
