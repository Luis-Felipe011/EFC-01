package org.tarefa.strategies.discount;
import org.tarefa.models.CustomerType;

public interface ICustomerDiscountStrategy {
    double applyCustomerDiscount(double total);
}
