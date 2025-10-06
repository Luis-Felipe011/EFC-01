package org.tarefa;
import org.tarefa.repositories.*;
import org.tarefa.models.*;

import java.time.LocalDateTime;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Customer cliente = new Customer("Joao Pedro", CustomerType.VIP);

        List<OrderItem> itens = new ArrayList<>();
        itens.add(new OrderItem("Teclado Mecânico", 250.0, 1, DiscountType.DESC10));
        itens.add(new OrderItem("Mouse Gamer", 150.0, 2, DiscountType.DESC20));
        itens.add(new OrderItem("Monitor 27\"", 1200.0, 1, DiscountType.NORMAL));

        Order pedido = new Order(cliente, itens, "PENDENTE", LocalDateTime.now());

        System.out.println("Total do pedido: " + pedido.getTotal());

        System.out.println("\nItens do pedido:");
        for (OrderItem item : pedido.getItems()) {
            System.out.println(item.getProductName() + " x" + item.getQuantity() +
                    " - Preço: " + item.getPrice() +
                    " - Desconto: " + item.getDiscountType());
        }

    }
}