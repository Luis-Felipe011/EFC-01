package services;

import models.Order;

public class NotificationService {

    public void sendEmail(String clientName, String message) {
        System.out.println("Email enviado para " + clientName + ": " + message);
    }

    public void notifyStatus(Order order, String newStatus) {
        String client = order.getClientName();
        switch (newStatus) {
            case "aprovado" -> System.out.println("SMS + Email enviados para " + client + ": Pedido aprovado!");
            case "enviado" -> System.out.println("Email enviado para " + client + ": Pedido enviado!");
            case "entregue" -> System.out.println("Email enviado para " + client + ": Pedido entregue!");
        }
    }
}
