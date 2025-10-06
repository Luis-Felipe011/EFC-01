package org.tarefa.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tarefa.models.Customer;
import org.tarefa.models.CustomerType;
import org.tarefa.models.Order;
import org.tarefa.models.OrderItem;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements IOrderRepository {

    private final Gson gson = new Gson();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public long save(Order order) {
        String sql = "INSERT INTO orders(customer_name, customer_type, items, total, status, creation_date) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, order.getCustomer().getName());
            pstmt.setString(2, order.getCustomer().getType().name());
            pstmt.setString(3, gson.toJson(order.getItems()));
            pstmt.setDouble(4, order.getTotal());
            pstmt.setString(5, order.getStatus());
            pstmt.setString(6, order.getCreationDate().format(formatter));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o pedido: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public Optional<Order> findById(long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os pedidos: " + e.getMessage());
        }
        return orders;
    }

    @Override
    public List<Order> findByCustomerName(String customerName) {
        String sql = "SELECT * FROM orders WHERE customer_name = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedidos por cliente: " + e.getMessage());
        }
        return orders;
    }


    @Override
    public void updateStatus(long id, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status do pedido: " + e.getMessage());
        }
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String customerName = rs.getString("customer_name");
        CustomerType customerType = CustomerType.valueOf(rs.getString("customer_type"));
        String itemsJson = rs.getString("items");
        double total = rs.getDouble("total");
        String status = rs.getString("status");
        LocalDateTime creationDate = LocalDateTime.parse(rs.getString("creation_date"), formatter);

        Type listType = new TypeToken<ArrayList<OrderItem>>() {}.getType();
        List<OrderItem> items = gson.fromJson(itemsJson, listType);

        Customer customer = new Customer(customerName, customerType);
        return new Order(id, customer, items, total, status, creationDate);
    }
}
