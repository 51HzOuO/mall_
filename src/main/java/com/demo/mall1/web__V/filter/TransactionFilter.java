package com.demo.mall1.web__V.filter;

import com.demo.mall1.utils.GetQueryRunner;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class TransactionFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } catch (Exception e) {
            if (e.getCause() instanceof SQLException) {
                try {
                    GetQueryRunner.getConnection().rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        GetQueryRunner.closeConnection();
    }
}
