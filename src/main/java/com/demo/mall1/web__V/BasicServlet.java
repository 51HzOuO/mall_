package com.demo.mall1.web__V;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class BasicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 使用hidden input传递action参数
            // 通过反射调用对应的方法
            // 子类只需要写 'action' 方法即可
            // 例如: public void login(HttpServletRequest req, HttpServletResponse resp) {}
            this.getClass().getDeclaredMethod(req.getParameter("action"), HttpServletRequest.class, HttpServletResponse.class).invoke(this, req, resp);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
