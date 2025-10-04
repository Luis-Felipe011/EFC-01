package org.tarefa.repositories;

import org.tarefa.models.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository {
    long save(Order order);
    Optional<Order> findById(long id);
    List<Order> findAll();
    List<Order> findByCustomerName(String customerName);
    void updateStatus(long id, String status);
}