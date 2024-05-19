package com.demo.mall1.services__C;

import com.demo.mall1.beans.Cart;

public interface OrderService {
    void saveOrder(Cart cart, Integer userID);
}
