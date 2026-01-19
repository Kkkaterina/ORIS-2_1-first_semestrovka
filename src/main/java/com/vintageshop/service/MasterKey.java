package com.vintageshop.service;

public class MasterKey {
    private static MasterKey instance;
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    private MasterKey() {
        this.userService = new UserService();
        this.productService = new ProductService();
        this.orderService = new OrderService();
    }

    public static MasterKey getInstance() {
        if (instance == null) {
            instance = new MasterKey();
        }
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
