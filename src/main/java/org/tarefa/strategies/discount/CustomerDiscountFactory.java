package org.tarefa.strategies.discount;

import org.tarefa.models.CustomerType;
import java.util.Map;

public class CustomerDiscountFactory {
    private static final Map<CustomerType, ICustomerDiscountStrategy> STRATEGIES = Map.of(
            CustomerType.NORMAL, new NormalClientDiscount(),
            CustomerType.VIP, new PercentageClientDiscount(0.05)
    );

    public static ICustomerDiscountStrategy getClientDiscountStrategy(CustomerType tipoDeCliente) {
        return STRATEGIES.getOrDefault(tipoDeCliente, new NormalClientDiscount());
    }
}
