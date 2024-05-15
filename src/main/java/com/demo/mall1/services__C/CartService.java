package com.demo.mall1.services__C;

import com.demo.mall1.beans.Cart;
import com.demo.mall1.beans.CartItem;
import com.demo.mall1.beans.Furn;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    void addCartItem(Cart cart, Furn furn);

    void setItemCount(Cart cart, Furn furn, int count);

    void deleteItem(Cart cart, Furn furn);

    void clear(Cart cart);

    int getTotalCount(Cart cart);

    BigDecimal getTotalPrice(Cart cart);

    List<CartItem> getItems(Cart cart);
}
