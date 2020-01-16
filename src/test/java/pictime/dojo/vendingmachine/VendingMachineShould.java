package pictime.dojo.vendingmachine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import pictime.dojo.vendingmachine.common.enums.Coin;
import pictime.dojo.vendingmachine.common.Messages;
import pictime.dojo.vendingmachine.common.enums.Product;
import pictime.dojo.vendingmachine.modules.InputDecoder;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineShould {

    public static final String BOGUS_COMMAND = "toto";
    private final InputStream sysInBackup = System.in;

    private final PrintStream sysoutBackup = System.out;


    /**
     * Construit une chaine a partir de chaque cmmande unitaire et les convertit en un tableau de byte qui servira a init un InputStream.
     * Ce dernier sera substitué a l'input stream systeme
     *
     * @param commands
     */
    private void initSystemWithChainOfInputs(String... commands) {
        StringBuffer chainOfCommands = new StringBuffer();
        Arrays.asList(commands).stream().forEach(chainOfCommands::append);
        ByteArrayInputStream in = new ByteArrayInputStream(chainOfCommands.toString().getBytes());
        System.setIn(in);
    }

    /**
     * Substitue au flux de sortie standard avec un bytearrayOutputStream.
     * cela nous permettra de lire ce qui aura été écrit par le programme
     *
     * @return
     */
    private ByteArrayOutputStream switchSystemOutputStreamToByteArray() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);
        System.setOut(printStream);
        return output;
    }


    @AfterEach
    private void restoreSystemStreams() {
        System.setIn(sysInBackup);
        System.setOut(sysoutBackup);
    }

    @Test
    public void give_correct_amount_of_credit_when_coins_are_added() {
        //Given
        initSystemWithChainOfInputs(
                Coin.CINQUANTECTS.getCode(),
                System.lineSeparator(),
                InputDecoder.EXIT_CODE);
        ByteArrayOutputStream byteArrayOutputStream = switchSystemOutputStreamToByteArray();
        VendingMachine machine = new VendingMachine();
        //When
        try {
            machine.switchOn();
        } catch (InterruptedException e) {
            fail(e);
        }
        //Then
        assertEquals(Messages.INPUT_MESSAGE
                        + System.lineSeparator() + Messages.VOTRE_CRÉDIT_EST_DE + Coin.CINQUANTECTS.getValue()
                        + System.lineSeparator()
                        + Messages.INPUT_MESSAGE
                        + System.lineSeparator(),
                byteArrayOutputStream.toString());
    }

    @Test
    public void display_error_message_when_given_incorrect_input() {
        //Given
        initSystemWithChainOfInputs(
                BOGUS_COMMAND,
                System.lineSeparator(),
                InputDecoder.EXIT_CODE);
        ByteArrayOutputStream byteArrayOutputStream = switchSystemOutputStreamToByteArray();
        VendingMachine machine = new VendingMachine();
        //When
        try {
            machine.switchOn();
        } catch (InterruptedException e) {
            fail(e);
        }
        //Then
        assertEquals(Messages.INPUT_MESSAGE
                        + System.lineSeparator()
                        + Messages.ENTRÉE_INVALIDE
                        + System.lineSeparator()
                        + Messages.INPUT_MESSAGE
                        + System.lineSeparator(),
                byteArrayOutputStream.toString());
    }


    @Test
    public void deliver_product_when_correct_amount_of_credit_is_available() {
        //Given
        initSystemWithChainOfInputs(
                Coin.DEUXEUROS.getCode(),
                System.lineSeparator(),
                Product.LEFFE.getName(),
                System.lineSeparator(),
                InputDecoder.EXIT_CODE);
        ByteArrayOutputStream byteArrayOutputStream = switchSystemOutputStreamToByteArray();
        VendingMachine machine = new VendingMachine();
        //When
        try {
            machine.switchOn();
        } catch (InterruptedException e) {
            fail(e);
        }
        //Then
        assertEquals(Messages.INPUT_MESSAGE
                        + System.lineSeparator()
                        + Messages.VOTRE_CRÉDIT_EST_DE
                        + Coin.DEUXEUROS.getValue()
                        + System.lineSeparator()
                        + Messages.INPUT_MESSAGE
                        + System.lineSeparator()
                        + Messages.PRODUIT_LIVRE + Product.LEFFE.getName()
                        + System.lineSeparator()
                        + Messages.INPUT_MESSAGE
                        + System.lineSeparator(),
                byteArrayOutputStream.toString());
    }

    @Test
    public void display_error_message_when_product_is_ordered_while_not_enough_credit_available() {
        //Given
        initSystemWithChainOfInputs(
                Coin.CINQUANTECTS.getCode(),
                System.lineSeparator(),
                Product.LEFFE.getName(),
                System.lineSeparator(),
                InputDecoder.EXIT_CODE);
        ByteArrayOutputStream byteArrayOutputStream = switchSystemOutputStreamToByteArray();
        VendingMachine machine = new VendingMachine();
        //When
        try {
            machine.switchOn();
        } catch (InterruptedException e) {
            fail(e);
        }
        //Then
        assertEquals(Messages.INPUT_MESSAGE
                        + System.lineSeparator()
                        + Messages.VOTRE_CRÉDIT_EST_DE
                        + Coin.CINQUANTECTS.getValue()
                        + System.lineSeparator()
                        + Messages.INPUT_MESSAGE
                        + System.lineSeparator()
                        + Messages.CRÉDIT_INSUFFISANT
                        + System.lineSeparator()
                        + Messages.INPUT_MESSAGE
                        + System.lineSeparator(),
                byteArrayOutputStream.toString());
    }

    @Test
    public void return_correct_amount_of_change_when_refund_command_is_given() {

        //Given
        initSystemWithChainOfInputs(
                Coin.CINQUANTECTS.getCode(),
                System.lineSeparator(),
                InputDecoder.REFUND_CODE,
                System.lineSeparator(),
                InputDecoder.EXIT_CODE);
        ByteArrayOutputStream byteArrayOutputStream = switchSystemOutputStreamToByteArray();
        VendingMachine machine = new VendingMachine();
        //When
        try {
            machine.switchOn();
        } catch (InterruptedException e) {
            fail(e);
        }
        //Then
        assertEquals(Messages.INPUT_MESSAGE
                        + System.lineSeparator()
                        + Messages.VOTRE_CRÉDIT_EST_DE
                        + Coin.CINQUANTECTS.getValue()
                        + System.lineSeparator()
                        + Messages.INPUT_MESSAGE
                        + System.lineSeparator()
                        + Messages.REMBOURSEMENT + " " + Coin.CINQUANTECTS.getCode()
                        + System.lineSeparator()
                        + Messages.INPUT_MESSAGE
                        + System.lineSeparator(),
                byteArrayOutputStream.toString());
    }

}
