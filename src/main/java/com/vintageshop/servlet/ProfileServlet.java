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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = MasterKey.getInstance().getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        boolean success = userService.updateUserProfile(user.getId(), name, email);

        if (success) {

            user.setName(name);
            user.setEmail(email);
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/profile?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/profile?error=email_taken");
        }
    }
}