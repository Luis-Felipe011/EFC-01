package org.tarefa.strategies.report;

import org.tarefa.models.Customer;
import org.tarefa.models.Order;
import org.tarefa.services.OrderService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerReportGenerator implements IReportGenerator {

    private final OrderService orderService;

    public CustomerReportGenerator(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void generate(List<Order> orders, String filename) {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("=== RELATÓRIO DE CLIENTES ===\n");

        Map<String, String> uniqueCustomers = orders.stream()
                .map(Order::getCustomer)
                .collect(Collectors.toMap(
                        Customer::getName,
                        c -> c.getType().toString(),
                        (existingValue, newValue) -> existingValue
                ));

        for (Map.Entry<String, String> entry : uniqueCustomers.entrySet()) {
            String customerName = entry.getKey();
            String customerType = entry.getValue();
            double totalSpent = orderService.getTotalSpentByCustomer(customerName);

            String line = String.format("Cliente: %s (%s) - Total gasto: R$%.2f\n",
                    customerName, customerType, totalSpent);
            reportContent.append(line);
        }

        System.out.println(reportContent.toString());
        saveToFile(filename, uniqueCustomers);
    }

    private void saveToFile(String filename, Map<String, String> customers) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Map.Entry<String, String> customer : customers.entrySet()) {
                writer.write(String.format("%s,%s\n", customer.getKey(), customer.getValue()));
            }
            System.out.println("Relatório de clientes salvo em " + filename);
        } catch (IOException e) {
            System.err.println("Erro ao salvar relatório de clientes: " + e.getMessage());
        }
    }
}
