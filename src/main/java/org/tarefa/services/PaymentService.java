package services;

import models.Order;
import services.payment.PaymentMethod;

public class PaymentService {

    private final PaymentMethod paymentMethod;

    public PaymentService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean processPayment(Order order, double amount) {
        return paymentMethod.processPayment(order, amount);
    }
}
