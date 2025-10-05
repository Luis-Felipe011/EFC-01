package services;

import models.Item;
import models.Order;
import models.ClientType;
import repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService {

    private final OrderRepository repository;
    private final NotificationService notificationService;

    public OrderService(OrderRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public int createOrder(String clientName, List<Item> items, ClientType clientType) {
        double total = calculateTotal(items, clientType);
        Order order = new Order(0, clientName, items, total, "pendente", LocalDateTime.now(), clientType);
        int id = repository.save(order);

        notificationService.sendEmail(clientName, "Pedido recebido!");
        return id;
    }

    public Order getOrder(int id) {
        return repository.findById(id);
    }

    public void updateStatus(int id, String newStatus) {
        Order order = repository.findById(id);
        if (order == null) return;

        order.setStatus(newStatus);
        repository.update(order);
        notificationService.notifyStatus(order, newStatus);
    }

    private double calculateTotal(List<Item> items, ClientType clientType) {
        double total = 0;
        for (Item item : items) {
            switch (item.getDiscountType()) {
                case "desc10" -> total += item.getPrice() * item.getQuantity() * 0.9;
                case "desc20" -> total += item.getPrice() * item.getQuantity() * 0.8;
                default -> total += item.getPrice() * item.getQuantity();
            }
        }
        if (clientType == ClientType.VIP) {
            total *= 0.95;
        }
        return total;
    }
}
