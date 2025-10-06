package org.tarefa.services;

import org.tarefa.models.Customer;
import org.tarefa.models.CustomerType;
import org.tarefa.models.Order;
import org.tarefa.models.OrderItem;
import org.tarefa.models.TotalCalculator;
import org.tarefa.repositories.IOrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderService {
    private final IOrderRepository orderRepository;
    private final INotificationService notificationService;
    private final TotalCalculator totalCalculator;

    private final Map<String, Integer> stock = Map.of("produto1", 100, "produto2", 50, "produto3", 75);

    public OrderService(IOrderRepository orderRepository, INotificationService notificationService, TotalCalculator totalCalculator) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
        this.totalCalculator = totalCalculator;
    }

    public long createOrder(Customer customer, List<OrderItem> items, boolean isSpecialOrder) {
        if (!isStockAvailable(items)) {
            System.err.println("Criação do pedido falhou devido a problemas de estoque.");
            return -1;
        }

        double total = totalCalculator.calculate(items, customer.getType(), isSpecialOrder);

        Order newOrder = new Order(customer, items, total, "pendente", LocalDateTime.now());

        long orderId = orderRepository.save(newOrder);

        String message = isSpecialOrder ? "Pedido especial recebido!" : "Pedido recebido!";
        notificationService.sendNotification(customer, message);

        return orderId;
    }

    public Optional<Order> getOrderById(long orderId) {
        return orderRepository.findById(orderId);
    }

    public void updateOrderStatus(long orderId, String newStatus) {
        orderRepository.findById(orderId).ifPresent(order -> {
            orderRepository.updateStatus(orderId, newStatus);

            switch (newStatus) {
                case "aprovado":
                    notificationService.sendPaymentApprovedNotification(order.getCustomer());
                    break;
                case "enviado":
                    notificationService.sendNotification(order.getCustomer(), "Seu pedido foi enviado!");
                    break;
                case "entregue":
                    notificationService.sendNotification(order.getCustomer(), "Seu pedido foi entregue!");
                    awardLoyaltyPoints(order);
                    break;
            }
        });
    }

    public double getTotalSpentByCustomer(String customerName) {
        return orderRepository.findByCustomerName(customerName)
                .stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    private void awardLoyaltyPoints(Order order) {
        int points = (int) order.getTotal();
        if (order.getCustomer().getType() == CustomerType.VIP) {
            points *= 2;
            System.out.printf("Cliente VIP %s ganhou %d pontos!\n", order.getCustomer().getName(), points);
        } else {
            System.out.printf("Cliente %s ganhou %d pontos!\n", order.getCustomer().getName(), points);
        }
    }

    private boolean isStockAvailable(List<OrderItem> items) {
        for (OrderItem item : items) {
            String productName = item.getProductName();
            if (!stock.containsKey(productName)) {
                System.err.printf("Produto %s não encontrado no estoque!\n", productName);
                return false;
            }
            if (stock.get(productName) < item.getQuantity()) {
                System.err.printf("Estoque insuficiente para %s! Disponível: %d, Solicitado: %d\n",
                        productName, stock.get(productName), item.getQuantity());
                return false;
            }
        }
        return true;
    }
}
