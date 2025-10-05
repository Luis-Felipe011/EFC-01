package org.tarefa.strategies.payment;

public class PixPaymentStrategy implements IPaymentStrategy {
    @Override
    public boolean process(double amount, long orderId) {
        System.out.println("Gerando QR Code PIX no valor de R$" + String.format("%.2f", amount) + " para o pedido #" + orderId);
        System.out.println("PIX recebido!");
        return true;
    }

    @Override
    public boolean autoApproves() {
        return true;
    }
}
