package com.vintageshop.servlet;

import com.vintageshop.service.MasterKey;
import com.vintageshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = MasterKey.getInstance().getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String search = request.getParameter("search");
        String category = request.getParameter("category");

        if (search != null && !search.trim().isEmpty()) {
            request.setAttribute("products", productService.searchProducts(search));
            request.setAttribute("searchTerm", search);
        } else if (category != null && !category.trim().isEmpty()) {
            request.setAttribute("products", productService.getProductsByCategory(category));
            request.setAttribute("selectedCategory", category);
        } else {
            request.setAttribute("products", productService.getAllAvailableProducts());
        }

        request.setAttribute("categories", productService.getAllCategories());

        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
    }
}