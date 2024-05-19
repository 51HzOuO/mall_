package com.demo.mall1.web__V.template;

import com.demo.mall1.beans.Cart;
import com.demo.mall1.beans.User;
import com.demo.mall1.services__C.OrderService;
import com.demo.mall1.services__C.impl.OrderServiceImpl;
import com.demo.mall1.web__V.BasicServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/order")
public class OrderServlet extends BasicServlet {
    private final OrderService orderService = new OrderServiceImpl();

    public void saveOrder(HttpServletRequest req, HttpServletResponse resp) {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //todo:验证库存
        req.getSession().removeAttribute("cart");
        User user = (User) req.getSession().getAttribute("user");
        orderService.saveOrder(cart, user.getId());
    }
}
