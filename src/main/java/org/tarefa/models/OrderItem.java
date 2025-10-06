package org.tarefa.models;

public class OrderItem {
    private String productName;
    private double price;
    private int quantity;
    private DiscountType discountType;

    public OrderItem(String productName, double price, int quantity, DiscountType discountType) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.discountType = discountType;
    }

    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public DiscountType getDiscountType() { return discountType; }
}