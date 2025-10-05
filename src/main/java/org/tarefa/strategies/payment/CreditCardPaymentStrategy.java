package org.tarefa.strategies.payment;

public class CreditCardPaymentStrategy implements IPaymentStrategy {
    @Override
    public boolean process(double amount, long orderId) {
        System.out.println("Processando pagamento com cartão de crédito no valor de R$" + String.format("%.2f", amount) + " para o pedido " + orderId);
        System.out.println("Cartão validado e pagamento aprovado!");
        return true;
    }

    @Override
    public boolean autoApproves() {
        return true;
    }
}
