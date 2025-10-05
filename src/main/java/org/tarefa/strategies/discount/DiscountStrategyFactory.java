package org.tarefa.strategies.discount;

import org.tarefa.models.DiscountType;

import java.util.Map;

public class DiscountStrategyFactory {
    private static final Map<DiscountType, IDiscountStrategy> STRATEGIES = Map.of(
            DiscountType.NORMAL, new NormalDiscountStrategy(),
            DiscountType.DESC10, new PercentageDiscountStrategy(0.10),
            DiscountType.DESC20, new PercentageDiscountStrategy(0.20)
    );

    public static IDiscountStrategy getStrategy(DiscountType type) {
        return STRATEGIES.getOrDefault(type, new NormalDiscountStrategy());
    }
}
