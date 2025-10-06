package org.tarefa.models;
import java.util.*;
import java.time.LocalDateTime;

public interface IOrder {
    long getId();
    Customer getCustomer();
    List<OrderItem> getItems();
    double getTotal();
    String getStatus();
    LocalDateTime getCreationDate();
    void setStatus(String status);
}
