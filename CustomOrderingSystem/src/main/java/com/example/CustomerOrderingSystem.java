package com.example;

import java.sql.*;
import java.util.Scanner;

public class CustomerOrderingSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/customer_order_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1. Add Customer");
                System.out.println("2. View Customers");
                System.out.println("3. Update Customer");
                System.out.println("4. Delete Customer");
                System.out.println("5. Add Item");
                System.out.println("6. View Items");
                System.out.println("7. Update Item");
                System.out.println("8. Delete Item");
                System.out.println("9. Create Order");
                System.out.println("10. View Orders");
                System.out.println("11. Delete Order");
                System.out.println("12. Add Item to Order");
                System.out.println("13. Delete Item from Order");
                System.out.println("14. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addCustomer(connection, scanner);
                        break;
                    case 2:
                        viewCustomers(connection);
                        break;
                    case 3:
                        updateCustomer(connection, scanner);
                        break;
                    case 4:
                        deleteCustomer(connection, scanner);
                        break;
                    case 5:
                        addItem(connection, scanner);
                        break;
                    case 6:
                        viewItems(connection);
                        break;
                    case 7:
                        updateItem(connection, scanner);
                        break;
                    case 8:
                        deleteItem(connection, scanner);
                        break;
                    case 9:
                        createOrder(connection, scanner);
                        break;
                    case 10:
                        viewOrders(connection);
                        break;
                    case 11:
                        deleteOrder(connection, scanner);
                        break;
                    case 12:
                        addItemToOrder(connection, scanner, choice);
                        break;
                    case 13:
                        deleteItemFromOrder(connection, scanner);
                        break;
                    case 14:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addCustomer(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter customer name: ");
            String name = scanner.next();
            String query = "INSERT INTO customers (name) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.executeUpdate();
                System.out.println("Customer added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    private static void viewCustomers(Connection connection) {
        String query = "SELECT id, name FROM customers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No customers found.");
                return;
            }
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.printf("ID: %d, Name: %s%n", id, name);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing customers: " + e.getMessage());
        }
    }

    private static void updateCustomer(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter customer ID to update: ");
            int id = scanner.nextInt();
            if (!customerExists(connection, id)) {
                System.out.println("Customer ID not found.");
                return;
            }
            System.out.print("Enter new customer name: ");
            String name = scanner.next();
            String query = "UPDATE customers SET name = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setInt(2, id);
                statement.executeUpdate();
                System.out.println("Customer updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    private static void deleteCustomer(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter customer ID to delete: ");
            int id = scanner.nextInt();
            if (!customerExists(connection, id)) {
                System.out.println("Customer ID not found.");
                return;
            }
            String query = "DELETE FROM customers WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
                System.out.println("Customer deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    private static void addItem(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter item name: ");
            String name = scanner.next();
            System.out.print("Enter item value: ");
            double value = scanner.nextDouble();
            String query = "INSERT INTO items (name, value) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setDouble(2, value);
                statement.executeUpdate();
                System.out.println("Item added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }

    private static void viewItems(Connection connection) {
        String query = "SELECT id, name, value FROM items";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No items found.");
                return;
            }
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double value = resultSet.getDouble("value");
                System.out.printf("ID: %d, Name: %s, Value: %.2f%n", id, name, value);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing items: " + e.getMessage());
        }
    }

    private static void updateItem(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter item ID to update: ");
            int id = scanner.nextInt();
            if (!itemExists(connection, id)) {
                System.out.println("Item ID not found.");
                return;
            }
            System.out.print("Enter new item name: ");
            String name = scanner.next();
            System.out.print("Enter new item value: ");
            double value = scanner.nextDouble();
            String query = "UPDATE items SET name = ?, value = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setDouble(2, value);
                statement.setInt(3, id);
                statement.executeUpdate();
                System.out.println("Item updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating item: " + e.getMessage());
        }
    }

    private static void deleteItem(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter item ID to delete: ");
            int id = scanner.nextInt();
            if (!itemExists(connection, id)) {
                System.out.println("Item ID not found.");
                return;
            }
            String query = "DELETE FROM items WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
                System.out.println("Item deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }

    private static void createOrder(Connection connection, Scanner scanner) {
        try {
            viewCustomers(connection);
            System.out.print("Enter customer ID for the order: ");
            int customerId = scanner.nextInt();
            if (!customerExists(connection, customerId)) {
                System.out.println("Customer ID not found.");
                return;
            }
            String query = "INSERT INTO orders (customer_id) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, customerId);
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        System.out.printf("Order created successfully with ID: %d%n", orderId);
                        addItemToOrder(connection, scanner, orderId); // Allow adding items to the order immediately
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
        }
    }

    private static void viewOrders(Connection connection) {
        String query = "SELECT o.id, c.name, GROUP_CONCAT(i.name) AS items " +
                "FROM orders o " +
                "JOIN customers c ON o.customer_id = c.id " +
                "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                "LEFT JOIN items i ON oi.item_id = i.id " +
                "GROUP BY o.id, c.name";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No orders found.");
                return;
            }
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String customerName = resultSet.getString("name");
                String items = resultSet.getString("items");
                if (items == null) {
                    items = "No items";
                }
                System.out.printf("Order ID: %d, Customer Name: %s, Items: %s%n", id, customerName, items);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing orders: " + e.getMessage());
        }
    }

    private static void deleteOrder(Connection connection, Scanner scanner) {
        try {
            String query = "SELECT o.id, c.name, GROUP_CONCAT(i.name SEPARATOR ', ') AS items " +
                           "FROM orders o " +
                           "JOIN customers c ON o.customer_id = c.id " +
                           "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                           "LEFT JOIN items i ON oi.item_id = i.id " +
                           "GROUP BY o.id, c.name";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No orders found.");
                    return;
                }
                System.out.println("Available orders:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String customerName = resultSet.getString("name");
                    String items = resultSet.getString("items");
                    if (items == null) {
                        items = "No items";
                    }
                    System.out.printf("Order ID: %d, Customer Name: %s, Items: %s%n", id, customerName, items);
                }
            }

            System.out.print("Enter the order ID to delete: ");
            int orderId = scanner.nextInt();
            if (!orderExists(connection, orderId)) {
                System.out.println("Order ID not found.");
                return;
            }

            String deleteQuery = "DELETE FROM orders WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, orderId);
                statement.executeUpdate();
                System.out.println("Order deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting order: " + e.getMessage());
        }
    }


    private static void addItemToOrder(Connection connection, Scanner scanner, int orderId2) {
        try {
            viewCustomers(connection);
            System.out.print("Enter customer ID: ");
            int customerId = scanner.nextInt();
            if (!customerExists(connection, customerId)) {
                System.out.println("Customer ID not found.");
                return;
            }

            viewOrdersByCustomer(connection, customerId);
            System.out.print("Enter order ID: ");
            int orderId = scanner.nextInt();
            if (!orderExists(connection, orderId)) {
                System.out.println("Order ID not found.");
                return;
            }

            viewItems(connection);
            System.out.print("Enter item ID: ");
            int itemId = scanner.nextInt();
            if (!itemExists(connection, itemId)) {
                System.out.println("Item ID not found.");
                return;
            }

            String query = "INSERT INTO order_items (order_id, item_id) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, orderId);
                statement.setInt(2, itemId);
                statement.executeUpdate();
                System.out.println("Item added to order successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding item to order: " + e.getMessage());
        }
    }

    private static void viewOrdersByCustomer(Connection connection, int customerId) {
        String query = "SELECT o.id, c.name, GROUP_CONCAT(i.name SEPARATOR ', ') AS items " +
                       "FROM orders o " +
                       "JOIN customers c ON o.customer_id = c.id " +
                       "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                       "LEFT JOIN items i ON oi.item_id = i.id " +
                       "WHERE c.id = ? " +
                       "GROUP BY o.id, c.name";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No orders found for this customer.");
                    return;
                }
                System.out.println("Available orders:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String customerName = resultSet.getString("name");
                    String items = resultSet.getString("items");
                    if (items == null) {
                        items = "No items";
                    }
                    System.out.printf("Order ID: %d, Customer Name: %s, Items: %s%n", id, customerName, items);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error viewing orders: " + e.getMessage());
        }
    }

    private static void deleteItemFromOrder(Connection connection, Scanner scanner) {
        try {
            viewCustomers(connection);
            System.out.print("Enter customer ID: ");
            int customerId = scanner.nextInt();
            if (!customerExists(connection, customerId)) {
                System.out.println("Customer ID not found.");
                return;
            }

            viewOrdersByCustomer(connection, customerId);
            System.out.print("Enter order ID: ");
            int orderId = scanner.nextInt();
            if (!orderExists(connection, orderId)) {
                System.out.println("Order ID not found.");
                return;
            }

            viewItemsInOrder(connection, orderId);
            System.out.print("Enter item ID: ");
            int itemId = scanner.nextInt();
            if (!itemExists(connection, itemId)) {
                System.out.println("Item ID not found.");
                return;
            }

            String query = "DELETE FROM order_items WHERE order_id = ? AND item_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, orderId);
                statement.setInt(2, itemId);
                statement.executeUpdate();
                System.out.println("Item deleted from order successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting item from order: " + e.getMessage());
        }
    }

    private static void viewItemsInOrder(Connection connection, int orderId) {
        String query = "SELECT i.id, i.name, i.value " +
                       "FROM items i " +
                       "JOIN order_items oi ON i.id = oi.item_id " +
                       "WHERE oi.order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No items found for this order.");
                    return;
                }
                System.out.println("Available items in order:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double value = resultSet.getDouble("value");
                    System.out.printf("Item ID: %d, Name: %s, Value: %.2f%n", id, name, value);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error viewing items in order: " + e.getMessage());
        }
    }


    private static boolean customerExists(Connection connection, int id) throws SQLException {
        String query = "SELECT 1 FROM customers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private static boolean itemExists(Connection connection, int id) throws SQLException {
        String query = "SELECT 1 FROM items WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private static boolean orderExists(Connection connection, int id) throws SQLException {
        String query = "SELECT 1 FROM orders WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}