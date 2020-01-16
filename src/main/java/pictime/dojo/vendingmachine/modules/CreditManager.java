package pictime.dojo.vendingmachine.modules;

import org.apache.commons.lang3.StringUtils;
import pictime.dojo.vendingmachine.common.ChangeHandler;
import pictime.dojo.vendingmachine.common.MoneyValueHandler;
import pictime.dojo.vendingmachine.common.enums.Coin;
import pictime.dojo.vendingmachine.common.enums.Event;
import pictime.dojo.vendingmachine.common.Messages;
import pictime.dojo.vendingmachine.common.enums.Product;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.List;

import static pictime.dojo.vendingmachine.common.EventHandler.isEventOfType;

/**
 * Gere le credit
 * Comptabilise le credit de la machine et decide si un ordre de commande peut etre honoré et rend la monnaie lorsuqe la commande est effectuée.
 *
 */
public class CreditManager implements PropertyChangeListener {

    private BigDecimal credit = BigDecimal.ZERO;

    PropertyChangeSupport eventBus;

    public CreditManager(PropertyChangeSupport eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (isEventOfType(Event.ADD_COIN, evt.getPropertyName())) {
            credit = MoneyValueHandler.roundBigDecimal(credit.add((BigDecimal) evt.getNewValue()));
            this.eventBus.firePropertyChange(Event.DISPLAY_MESSAGE.getName(), "", Messages.VOTRE_CRÉDIT_EST_DE + credit);
        }

        if (isEventOfType(Event.PRODUCT_ORDER, evt.getPropertyName())) {
            Product orderedProduct = (Product) evt.getNewValue();
            if (isThereEnoughCreditForPurchase(orderedProduct.getPrice())) {
                this.eventBus.firePropertyChange(Event.DELIVER_PRODUCT.getName(), "", orderedProduct);
            } else {
                this.eventBus.firePropertyChange(Event.DISPLAY_MESSAGE.getName(), "", Messages.CRÉDIT_INSUFFISANT);
            }
        }

        if (isEventOfType(Event.PRODUCT_DELIVERED, evt.getPropertyName())) {
            Product orderedProduct = (Product) evt.getNewValue();
            this.credit = MoneyValueHandler.roundBigDecimal(this.credit.subtract(orderedProduct.getPrice()));
        }

        if (isEventOfType(Event.REFUND, evt.getPropertyName())) {
            List<Coin> change = ChangeHandler.makeChange(this.credit);
            String displayChain = change.stream().reduce(Messages.REMBOURSEMENT, (message, current) -> message + StringUtils.SPACE + current.getCode(), String::concat);
            this.credit = BigDecimal.ZERO;
            this.eventBus.firePropertyChange(Event.DISPLAY_MESSAGE.getName(), "", displayChain);
        }
    }



    private boolean isThereEnoughCreditForPurchase(BigDecimal price) {
        return price.compareTo(this.credit) <= 0;
    }
}
