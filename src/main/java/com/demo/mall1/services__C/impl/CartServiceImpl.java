package com.demo.mall1.services__C.impl;

import com.demo.mall1.beans.Cart;
import com.demo.mall1.beans.CartItem;
import com.demo.mall1.beans.Furn;
import com.demo.mall1.services__C.CartService;

import java.math.BigDecimal;
import java.util.List;

public class CartServiceImpl implements CartService {

    @Override
    public void addCartItem(Cart cart, Furn furn) {
        cart.addCartItem(furn);
    }

    @Override
    public int setItemCount(Cart cart, Furn furn, int count) {
        return cart.setItemCount(furn, count);
    }

    @Override
    public void deleteItem(Cart cart, Furn furn) {
        cart.deleteItem(furn);
    }

    @Override
    public void clear(Cart cart) {
        cart.clear();
    }

    @Override
    public int getTotalCount(Cart cart) {
        return cart.getTotalCount();
    }

    @Override
    public BigDecimal getTotalPrice(Cart cart) {
        return cart.getTotalPrice();
    }

    @Override
    public List<CartItem> getItems(Cart cart) {
        return cart.getItems();
    }
}
