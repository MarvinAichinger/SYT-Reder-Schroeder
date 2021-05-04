/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package producerconsumertest;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robreder
 */
public class MessagePool {

    private String[] buffer;
    private int producerPosition = 0;
    private int consumerPosition = 0;
    private Semaphore canConsume;
    private Semaphore canProduce;

    /***
     * Constructor MessagePool with size of store as parameter
     * @param size 
     */
    public MessagePool(int size) {
        buffer = new String[size];
        canConsume = new Semaphore(0);
        canProduce = new Semaphore(size);
    }

    /***
     * get() is used from the consumer
     * @return 
     */
    public String get() {
        String value;
        canConsume.acquireUninterruptibly();
        synchronized (this) {
            value = this.buffer[consumerPosition];
            consumerPosition = (++consumerPosition) % buffer.length;
        }
        canProduce.release();
        return value;
    }

    /***
     * put is used from the producer to put an object to the data pool
     * @param value 
     */
    public void put(String value, String message) {
        canProduce.acquireUninterruptibly();
        synchronized (this) {
            this.buffer[producerPosition] = value;
            /*if (producerPosition == buffer.length) {
                producerPosition = 0;
            }*/
            producerPosition = (++producerPosition) % buffer.length;
        }
        System.out.println(message);
        canConsume.release();
    }
}
