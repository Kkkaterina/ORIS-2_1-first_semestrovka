package com.vintageshop.service;

import com.vintageshop.dao.ProductDao;
import com.vintageshop.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }


    public List<Product> getAllAvailableProducts() {
        return productDao.findAll();
    }


    public Optional<Product> getProductById(long id) {
        return productDao.findById(id);
    }


    public List<Product> searchProducts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllAvailableProducts();
        }
        return productDao.searchProducts(searchTerm.trim());
    }

    public List<Product> getProductsByCategory(String category) {
        return productDao.findByCategory(category);
    }

    public List<String> getAllCategories() {
        List<Product> products = getAllAvailableProducts();
        return products.stream()
                .map(Product::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}