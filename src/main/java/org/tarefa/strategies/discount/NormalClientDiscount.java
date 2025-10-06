package org.tarefa.strategies.discount;

public class NormalClientDiscount implements ICustomerDiscountStrategy{
    @Override
    public double applyCustomerDiscount(double total) {
        return total;
    }
}
