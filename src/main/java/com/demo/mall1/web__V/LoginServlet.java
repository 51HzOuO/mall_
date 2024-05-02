package com.demo.mall1.web__V;

import com.demo.mall1.beans.Info;
import com.demo.mall1.beans.User;
import com.demo.mall1.services__C.UserService;
import com.demo.mall1.services__C.impl.UserServiceImpl;
import com.demo.mall1.utils.GetGson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/loginService")
public class LoginServlet extends HttpServlet {
    public static final ThreadLocal<Object> returnInfo = new ThreadLocal<>();
    private final UserService memberService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User user = GetGson.getGson().fromJson(req.getReader(), User.class);
        Info info = GetGson.getGson().fromJson(req.getReader(), Info.class);
        if (info.getCode() == null) {
            //Login
            User user = new User();
            user.setUsername(info.getUsername());
            user.setPassword(info.getPassword());
            returnInfo.set(user);
            if (memberService.login(user)) {
                user = (User) returnInfo.get();
                //Online users mapping
                Map<String, User> users = (Map<String, User>) req.getServletContext().getAttribute("users");
                users.put(user.getUsername(), user);
                req.getSession().setAttribute("user", user);
                resp.getWriter().write("ok");
            } else {
                resp.getWriter().write(returnInfo.get().toString());
            }
        } else {
            //Register
            if (memberService.register(info)) {
                User user = (User) returnInfo.get();
                String json = GetGson.getGson().toJson(user);
                resp.getWriter().write(json);
            } else {
                resp.getWriter().write(returnInfo.get().toString());
            }
        }

    }
}
