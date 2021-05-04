/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package producerconsumertest;

import static java.lang.Thread.sleep;

/**
 *
 * @author robreder
 */
class Producer extends Thread {

    private final MessagePool _messagePool;
    private String _producerName;
    private int _productionAmount;

    public Producer(MessagePool pool, String name, int productionAmount) {
        _messagePool = pool;
        _producerName = name;
        _productionAmount = productionAmount;
    }

    @Override
    public void run() {
        String msg = "";
        for (int i = 1; i <= _productionAmount; i++) {
            
            try {
                // Produce something and put it to the pool
                sleep((int) (Math.random() * 100)); // production needs some time
                msg = "Msg:(" + i + ") from " + _producerName;
            } catch (InterruptedException e) {
            }
            
            
            _messagePool.put(msg, "Producer " + _producerName + " put: " + msg);
            
        }
    }
}

