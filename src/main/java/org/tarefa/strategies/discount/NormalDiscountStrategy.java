package org.tarefa.strategies.discount;

public class NormalDiscountStrategy implements IDiscountStrategy {
    @Override
    public double applyDiscount(double price, int quantity) {
        return price * quantity;
    }
}

