package pictime.dojo.vendingmachine.common;

import pictime.dojo.vendingmachine.common.enums.Coin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Regroupe les règles métier concernant le calcul de monnaie
 */
public class ChangeHandler {


    /**
     * Renvoie la liste des pièces permettant de faire la monnaie sur le montant à rendre
     * @param amountToRefund
     */
    public static List<Coin> makeChange(BigDecimal amountToRefund) {
        List<Coin> change = new ArrayList<Coin>();
        addCoinToChange(amountToRefund, change);
        return change;
    }

    /**
     * Calcule recursivement la plus grosse pièce inferieure au montant a rembourser et l'ajoute a la monnaie.
     * Lorsqu'il reste strictement moins de 5 cts la machine ne rends pas de monnaie
     * @param amountToRefund
     * @param change
     */
    private static void addCoinToChange(BigDecimal amountToRefund, List<Coin> change) {
        if(amountToRefund.compareTo(Coin.CINQCTS.getValue()) >=0) {
            Coin highestCoinValueToRefund = Arrays.stream(Coin.values())
                    .reduce(Coin.CINQCTS,
                    (highestCoin, currentCoin) -> (
                            (currentCoin.getValue().compareTo(highestCoin.getValue()) > 0) && currentCoin.getValue().compareTo(amountToRefund) <= 0 ) ? currentCoin : highestCoin
                    );
            change.add(highestCoinValueToRefund);
            BigDecimal amountLeftToRefund = MoneyValueHandler.roundBigDecimal(amountToRefund.subtract(highestCoinValueToRefund.getValue()));
            addCoinToChange(amountLeftToRefund,change);
        } else {
            return;
        }
    }

}
