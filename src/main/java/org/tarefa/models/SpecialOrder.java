package org.tarefa.models;

import java.time.LocalDateTime;
import java.util.List;

public class SpecialOrder implements IOrder {

    private static final double ADICIONAL_FIXO = 0.15;
    private long id;
    private Customer customer;
    private List<OrderItem> items;
    private double total;
    private String status;
    private LocalDateTime creationDate;


    public SpecialOrder(long id, Customer cliente, List<OrderItem> i, String status) {
        this.id = id;
        this.customer = cliente;
        this.items = i;
        setTotal();
        this.status = status;
        this.creationDate = LocalDateTime.now();
    }


    private void setTotal() {
        TotalCalculator calculadoraDeTotalDoPedido = new TotalCalculator();
        this.total = calculadoraDeTotalDoPedido.calcularTotal(this);
        double taxaExtra = this.total * ADICIONAL_FIXO;
        this.total += taxaExtra;
    }

    @Override
    public long getId() {
        // TODO Auto-generated method stub
        return this.id;
    }

    @Override
    public Customer getCustomer() {
        // TODO Auto-generated method stub
        return this.customer;
    }

    @Override
    public List<OrderItem> getItems() {
        // TODO Auto-generated method stub
        return this.items;
    }

    @Override
    public double getTotal() {
        // TODO Auto-generated method stub
        return this.total;
    }

    @Override
    public String getStatus() {
        // TODO Auto-generated method stub
        return this.status;
    }

    @Override
    public LocalDateTime getCreationDate() {
        // TODO Auto-generated method stub
        return this.creationDate;
    }

    @Override
    public void setStatus(String status) {
        // TODO Auto-generated method stub
        this.status = status;

    }

}
