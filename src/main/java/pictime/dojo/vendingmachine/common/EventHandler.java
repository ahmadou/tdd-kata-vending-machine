package pictime.dojo.vendingmachine.common;

import pictime.dojo.vendingmachine.common.enums.Event;

/**
 * Methodes utilitaires permettant d'analyser les evenements
 */
public class EventHandler {

    public static boolean isEventOfType(Event type, String eventName) {
        return Event.valueOf(eventName) == type;
    }
}
