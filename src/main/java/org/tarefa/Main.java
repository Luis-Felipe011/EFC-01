package org.tarefa;

import org.tarefa.models.Customer;
import org.tarefa.models.CustomerType;
import org.tarefa.models.DiscountType;
import org.tarefa.models.OrderItem;
import org.tarefa.models.TotalCalculator;
import org.tarefa.repositories.DatabaseManager;
import org.tarefa.repositories.IOrderRepository;
import org.tarefa.repositories.OrderRepositoryImpl;
import org.tarefa.services.INotificationService;
import org.tarefa.services.NotificationService;
import org.tarefa.services.OrderService;
import org.tarefa.services.PaymentService;
import org.tarefa.services.ReportService;
import org.tarefa.strategies.payment.BoletoPaymentStrategy;
import org.tarefa.strategies.payment.CreditCardPaymentStrategy;
import org.tarefa.strategies.payment.IPaymentStrategy;
import org.tarefa.strategies.payment.PixPaymentStrategy;
import org.tarefa.strategies.report.CustomerReportGenerator;
import org.tarefa.strategies.report.IReportGenerator;
import org.tarefa.strategies.report.SalesReportGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();

        IOrderRepository orderRepository = new OrderRepositoryImpl();
        INotificationService notificationService = new NotificationService();
        TotalCalculator totalCalculator = new TotalCalculator();

        OrderService orderService = new OrderService(orderRepository, notificationService, totalCalculator);

        Map<String, IPaymentStrategy> paymentStrategies = new HashMap<>();
        paymentStrategies.put("cartao", new CreditCardPaymentStrategy());
        paymentStrategies.put("pix", new PixPaymentStrategy());
        paymentStrategies.put("boleto", new BoletoPaymentStrategy());
        PaymentService paymentService = new PaymentService(paymentStrategies, orderService);

        Map<String, IReportGenerator> reportGenerators = new HashMap<>();
        reportGenerators.put("vendas", new SalesReportGenerator());
        reportGenerators.put("clientes", new CustomerReportGenerator(orderService));
        ReportService reportService = new ReportService(reportGenerators, orderRepository);

        System.out.println("\nIniciando simulação do sistema da loja...\n");

        Customer customer1 = new Customer("João Silva", CustomerType.NORMAL);
        List<OrderItem> items1 = Arrays.asList(
                new OrderItem("produto1", 100.0, 2, DiscountType.NORMAL),
                new OrderItem("produto2", 50.0, 1, DiscountType.DESC10)
        );

        long orderId1 = orderService.createOrder(customer1, items1, false);
        if (orderId1 > 0) {
            System.out.println("Pedido #" + orderId1 + " criado com sucesso!");
            paymentService.processPayment(orderId1, "cartao", 245.0);
            orderService.updateOrderStatus(orderId1, "enviado");
            orderService.updateOrderStatus(orderId1, "entregue");
        }
        System.out.println("-------------------------------------\n");

        Customer customer2 = new Customer("Maria Santos", CustomerType.VIP);
        List<OrderItem> items2 = Arrays.asList(
                new OrderItem("produto3", 200.0, 1, DiscountType.DESC20)
        );

        long orderId2 = orderService.createOrder(customer2, items2, false);
        if (orderId2 > 0) {
            System.out.println("Pedido #" + orderId2 + " criado com sucesso!");
            paymentService.processPayment(orderId2, "pix", 152.00);
        }
        System.out.println("-------------------------------------\n");

        long specialOrderId = orderService.createOrder(customer1, items1, true);
        if (specialOrderId > 0) {
            System.out.println("Pedido Especial #" + specialOrderId + " criado com sucesso!");
            paymentService.processPayment(specialOrderId, "boleto", 281.75);
        }
        System.out.println("-------------------------------------\n");

        System.out.println("Gerando relatórios...");
        reportService.generateReport("vendas", "rel_vendas.txt");
        System.out.println();
        reportService.generateReport("clientes", "rel_clientes.txt");

        System.out.println("\nSimulação concluída.");
    }
}
