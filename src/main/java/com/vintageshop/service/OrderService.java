package com.vintageshop.service;

import com.vintageshop.dao.OrderDao;
import com.vintageshop.dao.ProductDao;
import com.vintageshop.entity.Order;
import com.vintageshop.entity.OrderItem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderDao orderDao;
    private final ProductDao productDao;

    public OrderService() {
        this.orderDao = new OrderDao();
        this.productDao = new ProductDao();
    }

    public Optional<Order> createOrder(long userId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return Optional.empty();
        }

        for (OrderItem item : items) {
            if (!productDao.findById(item.getProductId()).isPresent() ||
                    !productDao.findById(item.getProductId()).get().isAvailable()) {
                return Optional.empty();
            }
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setItems(new ArrayList<>(items));

        orderDao.save(order);

        return Optional.of(order);
    }

    public Optional<Order> getOrderById(long orderId, long userId) {
        Optional<Order> order = orderDao.findById(orderId);
        if (order.isPresent() && order.get().getUserId() == userId) {
            return order;
        }
        return Optional.empty();
    }


}