package com.demo.mall1.web__V;

import com.demo.mall1.beans.RegisterInfo;
import com.demo.mall1.beans.User;
import com.demo.mall1.services__C.UserService;
import com.demo.mall1.services__C.UserServiceImpl;
import com.demo.mall1.utils.GetGson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    public static final ThreadLocal<String> returnInfo = new ThreadLocal<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService memberService = new UserServiceImpl();
        RegisterInfo registerInfo = GetGson.getGson().fromJson(req.getReader(), RegisterInfo.class);
        if (memberService.register(registerInfo)) {
            try {
                User user = memberService.getUserByUsername(registerInfo.getUsername());
                String json = GetGson.getGson().toJson(user);
                resp.getWriter().write(json);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.getWriter().write(returnInfo.get());
        }
    }
}
