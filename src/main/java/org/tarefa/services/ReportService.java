package org.tarefa.services;

import org.tarefa.models.Order;
import org.tarefa.repositories.IOrderRepository;
import org.tarefa.strategies.report.IReportGenerator;

import java.util.List;
import java.util.Map;

public class ReportService {
    private final Map<String, IReportGenerator> reportGenerators;
    private final IOrderRepository orderRepository;

    public ReportService(Map<String, IReportGenerator> reportGenerators, IOrderRepository orderRepository) {
        this.reportGenerators = reportGenerators;
        this.orderRepository = orderRepository;
    }

    public void generateReport(String type, String filename) {
        IReportGenerator generator = reportGenerators.get(type);
        if (generator == null) {
            System.err.println("Tipo de relatório desconhecido: " + type);
            return;
        }

        List<Order> allOrders = orderRepository.findAll();
        generator.generate(allOrders, filename);
    }
}
