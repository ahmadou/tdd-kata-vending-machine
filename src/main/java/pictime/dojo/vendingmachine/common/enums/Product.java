package pictime.dojo.vendingmachine.common.enums;

import pictime.dojo.vendingmachine.common.MoneyValueHandler;

import java.math.BigDecimal;

/**
 * Enumère les produits vendus par la machine
 */
public enum Product {

    BUDWEISER("BUDWEISER",0.65), LEFFE("LEFFE",1.10), PAIXDIEU("PAIXDIEU", 1.75);

    /**
     * Nom du produit
     */
    private String name;

    /**
     * Prix en euros
     */
    private double price;

    private Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return MoneyValueHandler.fromDouble(price);
    }

    public String getName() {
        return name;
    }
}
