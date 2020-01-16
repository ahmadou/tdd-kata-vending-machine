package pictime.dojo.vendingmachine.modules;

import org.apache.commons.lang3.StringUtils;
import pictime.dojo.vendingmachine.common.enums.Coin;
import pictime.dojo.vendingmachine.common.enums.Event;
import pictime.dojo.vendingmachine.common.Messages;
import pictime.dojo.vendingmachine.common.enums.Product;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static pictime.dojo.vendingmachine.common.EventHandler.*;

/**
 * Analyse les saisies utilisateurs et emets des evenements en fonction.
 */
public class InputDecoder implements PropertyChangeListener {

    PropertyChangeSupport eventBus;

    public static final String EXIT_CODE = "bye";

    public static final String REFUND_CODE = "ref";

    public InputDecoder(PropertyChangeSupport eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (isEventOfType(Event.MACHINE_POWERED, evt.getPropertyName()) && evt.getNewValue() == Boolean.TRUE) {
            readInput();
        }
    }

    /**
     * Lits les entrees depuis la ligne de commande
     */
    private void readInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                display(Messages.INPUT_MESSAGE);
                String input = scanner.nextLine();
                if (StringUtils.isBlank(input)) {
                    continue;
                }
                if (EXIT_CODE.equals(input)) {
                    this.eventBus.firePropertyChange(Event.MACHINE_POWERED.getName(), true, false);
                    break;
                }
                if (REFUND_CODE.equals(input)) {
                    this.eventBus.firePropertyChange(Event.REFUND.getName(), false, true);
                    continue;
                }
                boolean coinWasAdded = fireEventIfCoinWasAdded(input);
                boolean productWasOrdered = fireEventIfProductWasOrdered(input);
                if (!coinWasAdded && !productWasOrdered) {
                    display(Messages.ENTRÉE_INVALIDE);
                }
            }
        }
    }

    private boolean fireEventIfCoinWasAdded(String input) {
        List<Coin> matchingCoins = Arrays.stream(Coin.values())
                .filter(coin -> coin.getCode().equals(input)).collect(Collectors.toList());
        matchingCoins
                .forEach(item -> {
                    eventBus.firePropertyChange(Event.ADD_COIN.getName(), 0, item.getValue());
                });
        return matchingCoins.size() > 0;
    }

    private boolean fireEventIfProductWasOrdered(String input) {
        List<Product> matchingProduct = Arrays.stream(Product.values())
                .filter(product -> product.getName().equals(input)).collect(Collectors.toList());
        matchingProduct
                .forEach(item -> {
                    eventBus.firePropertyChange(Event.PRODUCT_ORDER.getName(), 0, item);
                });
        return matchingProduct.size() > 0;
    }


    private void display(String message) {
        this.eventBus.firePropertyChange(Event.DISPLAY_MESSAGE.getName(), "", message);
    }
}
