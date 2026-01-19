package com.vintageshop.servlet;

import com.vintageshop.entity.User;
import com.vintageshop.service.MasterKey;
import com.vintageshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = MasterKey.getInstance().getOrderService();
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

        request.getRequestDispatcher("/jsp/orders.jsp").forward(request, response);
    }
}