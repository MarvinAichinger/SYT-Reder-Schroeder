package at.htl;

public class BusyWaitingCounter extends Counter {

    private boolean entryAllowed = true;

    @Override
    public void increment() {
        /**
         * Assambler:
         * 1. LOAD reg1, #value     Lade Wert der Variablen 'value' aus dem RAM in das CPU-Register 'reg1' (Accumulator).
         * 2. ADD reg1, 1           ALU rechnet, in 'reg1' steht das Ergebnis.
         * 3. STORE reg1, #value    Speichere Ergebnis aus 'reg1' in die RAM-Variable 'value'
         */

        //Entry Section start
        while (!entryAllowed) {
            //do nothing => eigentlich busy waiting
            Thread.yield();
            //Thread.onSpinWait();
        }

        /**
         * Assembler:
         * 2-3 Befehle
         */
        entryAllowed = false;
        //Entry section end

        //Critical Section start
        value++;
        //Critical Section end

        //Exit section start
        entryAllowed = true;
        //Exit section end
    }
}
