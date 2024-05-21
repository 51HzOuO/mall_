package com.demo.mall1.services__C;

import com.demo.mall1.beans.Cart;

import java.sql.SQLException;

public interface OrderService {
    String saveOrder(Cart cart, Integer userID) throws SQLException;
}
