package org.tarefa.strategies.discount;

public class PercentageClientDiscount implements ICustomerDiscountStrategy{
    private final double discount;
    PercentageClientDiscount(double discount){
        this.discount = discount;
    }

    @Override
    public double applyCustomerDiscount(double total) {

        return total - total * discount;
    }
}
