package org.tarefa.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:loja.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " customer_name TEXT NOT NULL," +
                " customer_type TEXT NOT NULL," +
                " items TEXT NOT NULL," +
                " total REAL NOT NULL," +
                " status TEXT NOT NULL," +
                " creation_date TEXT NOT NULL" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Sucesso");
        } catch (SQLException e) {
            System.err.println("Erro" + e.getMessage());
        }
    }
}
