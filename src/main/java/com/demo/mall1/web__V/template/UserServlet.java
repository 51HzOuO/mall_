package com.demo.mall1.web__V.template;

import com.demo.mall1.services__C.UserService;
import com.demo.mall1.services__C.impl.UserServiceImpl;
import com.demo.mall1.web__V.BasicServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends BasicServlet {
    public static final ThreadLocal<Object> returnInfo = new ThreadLocal<>();
    private final UserService userService = new UserServiceImpl();

    public void verifyAdministrator(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (userService.login(username, password)) {
            req.getSession().setAttribute("user", returnInfo.get());
            req.getSession().removeAttribute("error");
            resp.sendRedirect("manage_menu");
        } else {
            req.getSession().setAttribute("error", returnInfo.get());
            resp.sendRedirect("manage_login");
        }
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("user");
        resp.sendRedirect(req.getContextPath());
    }

    public void checkUserName(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String username = req.getParameter("username");
        if (userService.isExist(username)) {
            resp.getWriter().write("用户名已存在");
            return;
        }
        if (!username.matches("^\\w{4,16}$")) {
            resp.getWriter().write("format-error");
        } else {
            resp.getWriter().write("ok");
        }
    }
}
