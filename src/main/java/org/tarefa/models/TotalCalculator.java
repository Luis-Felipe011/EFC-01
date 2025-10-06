package org.tarefa.models;

import org.tarefa.strategies.discount.CustomerDiscountFactory;
import org.tarefa.strategies.discount.DiscountStrategyFactory;
import org.tarefa.strategies.discount.IDiscountStrategy;
import org.tarefa.strategies.discount.ICustomerDiscountStrategy;

import java.util.List;

public class TotalCalculator {

    private static final double SPECIAL_ORDER_FEE = 1.15;

    public double calculate(List<OrderItem> items, CustomerType customerType, boolean isSpecialOrder) {
        double subtotal = 0;

        for (OrderItem item : items) {
            IDiscountStrategy strategy = DiscountStrategyFactory.getStrategy(item.getDiscountType());
            subtotal += strategy.applyDiscount(item.getPrice(), item.getQuantity());
        }

        ICustomerDiscountStrategy customerDiscount = CustomerDiscountFactory.getClientDiscountStrategy(customerType);
        subtotal = customerDiscount.applyCustomerDiscount(subtotal);

        if (isSpecialOrder) {
            subtotal *= SPECIAL_ORDER_FEE;
        }

        return subtotal;
    }
}
