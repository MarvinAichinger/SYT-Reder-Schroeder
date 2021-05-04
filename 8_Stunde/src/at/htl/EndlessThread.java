package at.htl;

public class EndlessThread extends Thread {

    @Override
    public void run() {
        while(true) {
            System.out.println("y");
        }
    }

}
