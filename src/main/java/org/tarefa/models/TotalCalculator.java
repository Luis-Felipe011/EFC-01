package org.tarefa.models;

import org.tarefa.strategies.discount.CustomerDiscountFactory;
import org.tarefa.strategies.discount.DiscountStrategyFactory;
import org.tarefa.strategies.discount.IDiscountStrategy;
import org.tarefa.strategies.discount.ICustomerDiscountStrategy;

import java.util.*;


public class TotalCalculator {
    public double calcularTotal(IOrder pedido) {
        List<OrderItem> itensDoPedido = pedido.getItems();
        double total = 0;

        for (OrderItem itemAtual : itensDoPedido) {
            IDiscountStrategy estrategiaDeDesconto = DiscountStrategyFactory.getStrategy(itemAtual.getDiscountType());
            total += estrategiaDeDesconto.applyDiscount(itemAtual.getPrice(), itemAtual.getQuantity());
        }
        ICustomerDiscountStrategy descontoCliente = CustomerDiscountFactory.getClientDiscountStrategy(pedido.getCustomer().getType());
        total = descontoCliente.applyCustomerDiscount(total);
        return total;

    }

}
