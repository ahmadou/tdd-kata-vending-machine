package pictime.dojo.vendingmachine;

import pictime.dojo.vendingmachine.common.enums.Event;
import pictime.dojo.vendingmachine.modules.CreditManager;
import pictime.dojo.vendingmachine.modules.InputDecoder;
import pictime.dojo.vendingmachine.modules.MessageDisplayer;
import pictime.dojo.vendingmachine.modules.ProductDispenser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Analyse les actios
 */
public class VendingMachine implements PropertyChangeListener {


    PropertyChangeSupport eventBus;

    public VendingMachine() {
        this.eventBus = new PropertyChangeSupport(this);
        this.eventBus.addPropertyChangeListener(this);

    }

    /**
     * Branche les module sur le bus d'evenements
     */
    private void initModules() {
        this.eventBus.addPropertyChangeListener(new InputDecoder(this.eventBus));
        this.eventBus.addPropertyChangeListener(new CreditManager(this.eventBus));
        this.eventBus.addPropertyChangeListener(new MessageDisplayer(this.eventBus));
        this.eventBus.addPropertyChangeListener(new ProductDispenser(this.eventBus));
    }

    /**
     * Allume la machine qui attendra les commandes en entrée pour les traiter
     */
    public void switchOn() throws InterruptedException {
        initModules();
        fireStartingEvent();
        synchronized (this) {
            this.wait();
        }
    }

    /**
     * L'evenement est lancé dans un nouveau thread afin de pouvoir poursuivre l'execution dans le thread principal
     */
    private void fireStartingEvent() {
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                eventBus.firePropertyChange(Event.MACHINE_POWERED.getName(), false, true);
            }
        });
        newThread.start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Event.valueOf(evt.getPropertyName()) == Event.MACHINE_POWERED && evt.getNewValue() == Boolean.FALSE) {
            synchronized (this) {
                this.notify();
            }
        }
    }
}
