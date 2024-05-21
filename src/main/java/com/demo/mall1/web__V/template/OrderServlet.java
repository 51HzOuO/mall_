package com.demo.mall1.web__V.template;

import com.demo.mall1.beans.Cart;
import com.demo.mall1.beans.User;
import com.demo.mall1.services__C.OrderService;
import com.demo.mall1.services__C.impl.OrderServiceImpl;
import com.demo.mall1.web__V.BasicServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/order")
public class OrderServlet extends BasicServlet {
    private final OrderService orderService = new OrderServiceImpl();

    public void saveOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("views/member/login.jsp");
            return;
        }
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null || cart.getTotalCount() == 0) {
            resp.sendRedirect("index.jsp");
            return;
        }

        //todo:验证库存
        req.getSession().removeAttribute("cart");
        req.getSession().setAttribute("orderId", orderService.saveOrder(cart, user.getId()));
        resp.sendRedirect("checkout.jsp");
    }
}
