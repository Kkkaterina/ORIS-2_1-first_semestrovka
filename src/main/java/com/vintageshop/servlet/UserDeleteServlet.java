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

@WebServlet("/profile/delete")
public class UserDeleteServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = MasterKey.getInstance().getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            userService.deleteUser(user.getId());

            session.invalidate();

            response.sendRedirect(request.getContextPath() + "/?success=account_deleted");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/profile?error=delete_failed");
        }
    }
}