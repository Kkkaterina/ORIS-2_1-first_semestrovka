package com.vintageshop.listener;

import com.vintageshop.service.MasterKey;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@javax.servlet.annotation.WebListener
public class WebListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        MasterKey serviceLocator = MasterKey.getInstance();
        context.setAttribute("serviceLocator", serviceLocator);

        context.setAttribute("appName", "Vintage Dishes Shop");
        context.setAttribute("version", "1.0");

        System.out.println("начало работы");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("конец работы");
    }
}