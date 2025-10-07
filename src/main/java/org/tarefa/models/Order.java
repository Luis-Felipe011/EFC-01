package org.tarefa.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long id;
    private Customer customer;
    private List<OrderItem> items;
    private double total;
    private String status;
    private LocalDateTime creationDate;

    public Order(long id, Customer customer, List<OrderItem> items, double total, String status, LocalDateTime creationDate) {
        this.id = id;
        this.customer = customer;
        this.items = items;
        this.total = total;
        this.status = status;
        this.creationDate = creationDate;
    }

    public Order(Customer customer, List<OrderItem> items, double total, String status, LocalDateTime creationDate) {
        this(0, customer, items, total, status, creationDate);
    }

    public long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public LocalDateTime getCreationDate() { return creationDate; }
}