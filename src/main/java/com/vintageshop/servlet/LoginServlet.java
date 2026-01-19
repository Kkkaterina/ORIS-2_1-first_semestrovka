package com.vintageshop.servlet;

import com.vintageshop.entity.User;
import com.vintageshop.service.MasterKey;
import com.vintageshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = MasterKey.getInstance().getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> userOptional = userService.authenticate(email, password);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            String redirect = request.getParameter("redirect");
            if (redirect != null && !redirect.isEmpty()) {
                response.sendRedirect(redirect);
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            request.setAttribute("error", "Неверный пароль или имэйл");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }
}
