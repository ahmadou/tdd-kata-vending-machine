import pictime.dojo.vendingmachine.VendingMachine;

import java.util.Scanner;

/**
 * Main Programm
 */
public class Main {


    public static void main (String...args) {
        VendingMachine machine = new VendingMachine();
        try {
            machine.switchOn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
