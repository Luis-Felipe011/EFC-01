package org.tarefa.services;

import org.tarefa.models.Customer;

public class NotificationService implements INotificationService {

    @Override
    public void sendNotification(Customer customer, String message) {
        System.out.println("Email enviado para " + customer.getName() + ": " + message);
    }

    @Override
    public void sendPaymentApprovedNotification(Customer customer) {
        System.out.println("Email enviado para " + customer.getName() + ": Pedido aprovado!");
        System.out.println("SMS enviado para " + customer.getName() + ": Pedido aprovado!");
    }
}
