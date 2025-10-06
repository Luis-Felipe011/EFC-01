package org.tarefa.services;

import org.tarefa.models.Order;
import org.tarefa.strategies.payment.IPaymentStrategy;

import java.util.Map;
import java.util.Optional;

public class PaymentService {
    private final Map<String, IPaymentStrategy> paymentStrategies;
    private final OrderService orderService;

    public PaymentService(Map<String, IPaymentStrategy> paymentStrategies, OrderService orderService) {
        this.paymentStrategies = paymentStrategies;
        this.orderService = orderService;
    }

    public boolean processPayment(long orderId, String method, double amountPaid) {
        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isEmpty()) {
            System.err.println("Pedido #" + orderId + " não encontrado para processar pagamento.");
            return false;
        }

        Order order = orderOpt.get();
        if (amountPaid < order.getTotal()) {
            System.err.println("Valor pago é insuficiente para o pedido #" + orderId);
            return false;
        }

        IPaymentStrategy strategy = paymentStrategies.get(method.toLowerCase());
        if (strategy == null) {
            System.err.println("Método de pagamento inválido: " + method);
            return false;
        }

        boolean success = strategy.process(amountPaid, orderId);
        if (success && strategy.autoApproves()) {
            orderService.updateOrderStatus(orderId, "aprovado");
        }
        return success;
    }
}
