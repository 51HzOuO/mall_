package com.demo.mall1.web__V.filter;

import com.demo.mall1.beans.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/manage_login")
public class MultiLoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = null;
        if ((user = (User) req.getSession().getAttribute("user")) != null && user.getType() == 1) {
            res.sendRedirect("manage_menu");
        } else {
            chain.doFilter(req, res);
        }
    }
}
