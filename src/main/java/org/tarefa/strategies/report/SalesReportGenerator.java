package org.tarefa.strategies.report;

import org.tarefa.models.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SalesReportGenerator implements IReportGenerator {
    @Override
    public void generate(List<Order> orders, String filename) {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("=== RELATÓRIO DE VENDAS ===\n");

        double totalGeral = 0;
        for (Order order : orders) {
            String line = String.format("Pedido #%d - Cliente: %s - Total: R$%.2f - Status: %s\n",
                    order.getId(), order.getCustomer().getName(), order.getTotal(), order.getStatus());
            reportContent.append(line);
            totalGeral += order.getTotal();
        }
        reportContent.append(String.format("\nTotal Geral: R$%.2f\n", totalGeral));

        System.out.println(reportContent.toString());
        saveToFile(filename, "Total de vendas: " + String.format("%.2f", totalGeral));
    }

    private void saveToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
            System.out.println("Relatório de vendas salvo em " + filename);
        } catch (IOException e) {
            System.err.println("Erro ao salvar relatório de vendas: " + e.getMessage());
        }
    }
}