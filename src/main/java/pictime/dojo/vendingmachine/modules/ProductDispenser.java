package pictime.dojo.vendingmachine.modules;

import pictime.dojo.vendingmachine.common.enums.Event;
import pictime.dojo.vendingmachine.common.Messages;
import pictime.dojo.vendingmachine.common.enums.Product;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static pictime.dojo.vendingmachine.common.EventHandler.isEventOfType;

/**
 * Gère la fourniture des produits
 */
public class ProductDispenser implements PropertyChangeListener {

    PropertyChangeSupport eventBus;

    public ProductDispenser(PropertyChangeSupport eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (isEventOfType(Event.DELIVER_PRODUCT,evt.getPropertyName())) {
            Product product = (Product)evt.getNewValue();
            this.eventBus.firePropertyChange(Event.DISPLAY_MESSAGE.getName(), "", Messages.PRODUIT_LIVRE + product.getName());
            this.eventBus.firePropertyChange(Event.PRODUCT_DELIVERED.getName(), "", product);
        }
    }
}
