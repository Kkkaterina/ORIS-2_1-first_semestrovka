package com.vintageshop.dao;

import com.vintageshop.entity.Order;
import com.vintageshop.entity.OrderItem;
import com.vintageshop.entity.Product;
import com.vintageshop.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao {

    public Optional<Order> findById(long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             statement.setLong(1, id);
             ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Order order = mapResultSetToOrder(resultSet);
                order.setItems(findOrderItems(order.getId()));
                return Optional.of(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void save(Order order) {
        String sql = "INSERT INTO orders (user_id, status) VALUES (?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, order.getUserId());
            statement.setString(2, order.getStatus());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long orderId = generatedKeys.getLong(1);
                        order.setId(orderId);

                        saveOrderItems(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItemToOrder(long orderId, long productId, int quantity) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, orderId);
            statement.setLong(2, productId);
            statement.setInt(3, quantity);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveOrderItems(Order order) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (OrderItem item : order.getItems()) {
                statement.setLong(1, order.getId());
                statement.setLong(2, item.getProductId());
                statement.setInt(3, item.getQuantity());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private List<OrderItem> findOrderItems(long orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.*, p.name, p.price, p.image_url " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.id " +
                "WHERE oi.order_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderItem item = new OrderItem();
                item.setId(resultSet.getLong("id"));
                item.setOrderId(resultSet.getLong("order_id"));
                item.setProductId(resultSet.getLong("product_id"));
                item.setQuantity(resultSet.getInt("quantity"));
                Product product = new Product();
                product.setId(resultSet.getLong("product_id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setImageUrl(resultSet.getString("image_url"));
                item.setProduct(product);

                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    private Order mapResultSetToOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setUserId(resultSet.getLong("user_id"));
        order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        order.setStatus(resultSet.getString("status"));
        return order;
    }
}