package com.demo.mall1.web__V.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter(urlPatterns = {"/manage_login", "/manage_menu", "/furn_manage.jsp", "/order.jsp"})
//@WebFilter(urlPatterns = {"/views/manage/*"})
public class ManageLoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect("../../manage_login");
        } else {
            chain.doFilter(req, res);
        }
    }
}
