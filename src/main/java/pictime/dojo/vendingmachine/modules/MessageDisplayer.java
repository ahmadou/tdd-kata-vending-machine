package pictime.dojo.vendingmachine.modules;

import pictime.dojo.vendingmachine.common.enums.Event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static pictime.dojo.vendingmachine.common.EventHandler.isEventOfType;

/**
 * Gère les messages de la machine
 */
public class MessageDisplayer implements PropertyChangeListener {

    PropertyChangeSupport eventBus;

    public MessageDisplayer(PropertyChangeSupport eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (isEventOfType(Event.DISPLAY_MESSAGE,evt.getPropertyName())) {
            System.out.println(evt.getNewValue());
        }
    }
}
