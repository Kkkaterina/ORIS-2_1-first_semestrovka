package com.vintageshop.filter;

import com.vintageshop.service.MasterKey;
import com.vintageshop.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@javax.servlet.annotation.WebFilter("/*")
public class WebFilter implements Filter {

    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userService = MasterKey.getInstance().getUserService();
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith("/static/")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.equals("/") || path.equals("/login") || path.equals("/register") || path.startsWith("/products")) {

            chain.doFilter(request, response);
            return;
        }

        if (isProtectedPage(path) && !isLoggedIn(session)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isProtectedPage(String path) {
        return path.startsWith("/order") || path.startsWith("/profile") || path.startsWith("/cart");
    }

    private boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("user") != null;
    }
}
