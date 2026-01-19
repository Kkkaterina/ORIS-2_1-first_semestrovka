package com.vintageshop.servlet;

import com.vintageshop.entity.User;
import com.vintageshop.service.MasterKey;
import com.vintageshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = MasterKey.getInstance().getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Map<Long, Integer> cart = getCart(session);
        request.setAttribute("cartItems", cart);
        request.setAttribute("productService", productService);
        request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Map<Long, Integer> cart = getCart(session);

        if ("add".equals(action)) {
            long productId = Long.parseLong(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
            session.setAttribute("cart", cart);

            response.sendRedirect(request.getContextPath() + "/?success=added_to_cart");

        } else if ("remove".equals(action)) {
            long productId = Long.parseLong(request.getParameter("productId"));
            cart.remove(productId);
            session.setAttribute("cart", cart);
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Long, Integer> getCart(HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}