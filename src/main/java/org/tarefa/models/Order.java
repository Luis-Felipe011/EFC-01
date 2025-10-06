package org.tarefa.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order implements IOrder {
    private long id;
    private Customer customer;
    private List<OrderItem> items;
    private double total;e
    private String status;
    private LocalDateTime creationDate;

    public Order(long id, Customer customer, List<OrderItem> items, String status, LocalDateTime creationDate) {
        this.id = id;
        this.customer = customer;
        this.items = items;
        setTotal();
        this.status = status;
        this.creationDate = creationDate;
    }

    public Order(Customer customer, List<OrderItem> items, String status, LocalDateTime creationDate) {
        this.customer = customer;
        this.items = items;
        setTotal();
        this.status = status;
        this.creationDate = creationDate;
    }

    private void setTotal(){
        TotalCalculator calculadoraDeTotalDoPedido = new TotalCalculator();
        this.total = calculadoraDeTotalDoPedido.calcularTotal(this);

    }
    public long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public LocalDateTime getCreationDate() { return creationDate; }

    public void setStatus(String status) {
        this.status = status;
    }
}
