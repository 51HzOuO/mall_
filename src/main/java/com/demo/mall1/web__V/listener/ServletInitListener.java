package com.demo.mall1.web__V.listener;

import com.demo.mall1.beans.User;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;

@WebListener
public class ServletInitListener implements ServletContextListener {
    public static String contextPath = "";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        contextPath = sce.getServletContext().getRealPath("/");
        sce.getServletContext().setAttribute("users", new HashMap<String, User>());
    }
}
