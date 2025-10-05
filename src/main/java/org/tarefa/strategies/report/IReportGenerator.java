package org.tarefa.strategies.report;

import org.tarefa.models.Order;
import java.util.List;

public interface IReportGenerator {
    void generate(List<Order> orders, String filename);
}