package at.htl;

public class Counter implements CounterInterface {

    protected int value = 0;

    public void increment() {
        /**
         * Assambler:
         * 1. LOAD reg1, #value     Lade Wert der Variablen 'value' aus dem RAM in das CPU-Register 'reg1' (Accumulator).
         * 2. ADD reg1, 1           ALU rechnet, in 'reg1' steht das Ergebnis.
         * 3. STORE reg1, #value    Speichere Ergebnis aus 'reg1' in die RAM-Variable 'value'
         */
        //Critical Section start
        value++;
        //Critical Section end
    }

    public int getValue() {
        return value;
    }


}
