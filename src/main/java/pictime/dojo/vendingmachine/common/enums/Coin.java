package pictime.dojo.vendingmachine.common.enums;

import pictime.dojo.vendingmachine.common.MoneyValueHandler;

import java.math.BigDecimal;

/**
 * Enumération des types de pièces gérées par la machine
 */
public enum Coin {

    CINQCTS(new BigDecimal(0.05), "5ct"), DIXCTS(new BigDecimal(0.10),"10ct"), CINQUANTECTS(new BigDecimal(0.50),"50ct"), UNEURO(new BigDecimal(1),"1e"), DEUXEUROS(new BigDecimal(2),"2e");

    BigDecimal value;
    String code;

    private Coin(BigDecimal value, String code) {
        this.value = value;
        this.code= code;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getValue() {
        return MoneyValueHandler.roundBigDecimal(value);
    }
}
