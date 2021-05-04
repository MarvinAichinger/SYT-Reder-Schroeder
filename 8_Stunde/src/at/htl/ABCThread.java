package at.htl;

import java.io.IOException;

public class ABCThread extends Thread {
    @Override
    public void run() {
        System.out.println("ABCThread " + this.getName() + " is starting...");

        for (int i = 'A'; i <= 'Z' ; i++) {
            System.out.print((char) i + " ");
            try {
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
