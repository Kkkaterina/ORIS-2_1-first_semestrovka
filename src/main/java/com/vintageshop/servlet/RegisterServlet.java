package com.vintageshop.servlet;

import com.vintageshop.service.MasterKey;
import com.vintageshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = MasterKey.getInstance().getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String name = request.getParameter("name");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Паспорт не совпадает");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        boolean success = userService.registerUser(email, password, name);

        if (success) {
            request.setAttribute("success", "Вы зарегались");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("error", "Email занят, поменяйте его");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
        }
    }
}