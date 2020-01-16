package pictime.dojo.vendingmachine.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * S'assure que les valeurs double ou BigDecimal utilisée pour les valeurs de prix sont tout arrondie de la même manière
 */
public class MoneyValueHandler {

    public static final int NEW_SCALE = 2;

    public static BigDecimal fromDouble(double value) {
        return new BigDecimal(value).setScale(NEW_SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal roundBigDecimal(BigDecimal value) {
        return value.setScale(NEW_SCALE, RoundingMode.HALF_UP);
    }
}
