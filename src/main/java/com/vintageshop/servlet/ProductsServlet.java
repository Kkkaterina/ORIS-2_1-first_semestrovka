package com.vintageshop.servlet;

import com.vintageshop.service.MasterKey;
import com.vintageshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = MasterKey.getInstance().getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("products", productService.getAllAvailableProducts());
        request.getRequestDispatcher("/jsp/products.jsp").forward(request, response);
    }
}