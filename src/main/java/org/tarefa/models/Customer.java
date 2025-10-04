package org.tarefa.models;

public class Customer {
    private String name;
    private CustomerType type;

    public Customer(String name, CustomerType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public CustomerType getType() {
        return type;
    }
}