package pictime.dojo.vendingmachine.common.enums;

/**
 * Enum des events de la machine
 */
public enum Event {
    MACHINE_POWERED("MACHINE_POWERED"),
    ADD_COIN("ADD_COIN"), DISPLAY_MESSAGE("DISPLAY_MESSAGE"),
    PRODUCT_ORDER("PRODUCT_ORDER"), DELIVER_PRODUCT("DELIVER_PRODUCT"),
    PRODUCT_DELIVERED("PRODUCT_DELIVERED"),
    REFUND("REFUND");

    private String name;

    private Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
