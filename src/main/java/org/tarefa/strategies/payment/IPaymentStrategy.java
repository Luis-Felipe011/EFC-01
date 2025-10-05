package org.tarefa.strategies.payment;

public interface IPaymentStrategy {
    boolean process(double amount, long orderId);
    boolean autoApproves();
}