package com.demo.mall1.services__C.impl;

import com.demo.mall1.beans.Cart;
import com.demo.mall1.beans.Order;
import com.demo.mall1.beans.OrderItem;
import com.demo.mall1.services__C.OrderService;
import com.demo.mall1.utils.GetQueryRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class OrderServiceImpl implements OrderService {

    @Override
    public void saveOrder(Cart cart, Integer userID) {
        Order order = new Order();
        order.setUserID(userID);
        order.setCreatTime(new Date());
        order.setPrice(cart.getTotalPrice());
        order.setStatus(0);

        ArrayList<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> new OrderItem(order.getID(), cartItem.getName(), cartItem.getPrice(), cartItem.getCount(), cartItem.getTotalPrice()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        order.setOrderItems(orderItems);


        //Repository layer
        try {
            GetQueryRunner.getQueryRunner().execute("insert into `order` values (?,?,?,?,?)", order.getID(), order.getCreatTime(), order.getPrice(), order.getStatus(), order.getUserID());
            for (OrderItem orderItem : order.getOrderItems()) {
                GetQueryRunner.getQueryRunner().execute("insert into order_item values (?,?,?,?,?)", orderItem.getName(), orderItem.getPrice(), orderItem.getCount(), orderItem.getTotalPrice(), order.getID());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
