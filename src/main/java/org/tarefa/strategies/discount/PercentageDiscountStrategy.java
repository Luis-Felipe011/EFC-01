package org.tarefa.strategies.discount;

public class PercentageDiscountStrategy implements IDiscountStrategy {
    private final double discountPercentage;

    public PercentageDiscountStrategy(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double applyDiscount(double price, int quantity) {
        double total = price * quantity;
        return total - (total * discountPercentage);
    }
}
