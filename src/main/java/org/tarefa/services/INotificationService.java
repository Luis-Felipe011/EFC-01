package org.tarefa.services;

import org.tarefa.models.Customer;

public interface INotificationService {
    void sendNotification(Customer customer, String message);
    void sendPaymentApprovedNotification(Customer customer);
}

