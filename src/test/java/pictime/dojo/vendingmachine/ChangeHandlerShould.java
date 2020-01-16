package pictime.dojo.vendingmachine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pictime.dojo.vendingmachine.common.ChangeHandler;
import pictime.dojo.vendingmachine.common.MoneyValueHandler;
import pictime.dojo.vendingmachine.common.enums.Coin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeHandlerShould {

    @ParameterizedTest
    @ValueSource(doubles = {1.5, 2, 1.75, 0.25, 0.10, 0.15,0.85})
    public void return_correct_amount_of_change(double amountToRefund) {

        BigDecimal convertedAmountToRefund = MoneyValueHandler.fromDouble(amountToRefund);
        List<Coin> change = ChangeHandler.makeChange(convertedAmountToRefund);
        System.out.println(change);
        BigDecimal totalChangeAmount = change.stream().reduce(BigDecimal.ZERO, (subtotal, element) -> MoneyValueHandler.roundBigDecimal(subtotal.add(element.getValue())), BigDecimal::add);
        assertEquals(convertedAmountToRefund,totalChangeAmount);
    }
}
