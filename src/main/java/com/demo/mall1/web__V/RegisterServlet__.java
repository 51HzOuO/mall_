package com.demo.mall1.web__V;

import com.demo.mall1.beans.Info;
import com.demo.mall1.beans.User;
import com.demo.mall1.services__C.UserService;
import com.demo.mall1.services__C.impl.UserServiceImpl;
import com.demo.mall1.utils.GetGson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet(urlPatterns = "/register")
public class RegisterServlet__ extends HttpServlet {
    public static final ThreadLocal<Object> returnInfo = new ThreadLocal<>();
    private final UserService memberService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Info info = GetGson.getGson().fromJson(req.getReader(), Info.class);
        if (memberService.register(info)) {
            User user = (User) returnInfo.get();
            String json = GetGson.getGson().toJson(user);
            resp.getWriter().write(json);
        } else {
            resp.getWriter().write(returnInfo.get().toString());
        }
    }
}
