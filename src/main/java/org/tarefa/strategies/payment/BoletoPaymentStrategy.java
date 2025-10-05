package org.tarefa.strategies.payment;

public class BoletoPaymentStrategy implements IPaymentStrategy {
    @Override
    public boolean process(double amount, long orderId) {
        System.out.println("Gerando boleto no valor de R$" + String.format("%.2f", amount) + " para o pedido " + orderId);
        System.out.println("Boleto gerado!");
        return true;
    }

    @Override
    public boolean autoApproves() {
        return false;
    }
}