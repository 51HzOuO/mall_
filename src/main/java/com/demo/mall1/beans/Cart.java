package com.demo.mall1.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Cart {
    private final Map<Integer, CartItem> items = new TreeMap<>();
    private int totalCount = 0;
    private BigDecimal totalPrice = new BigDecimal(0);

    //add one at a time
    public void addCartItem(Furn furn) {
        this.totalCount++;
        CartItem cartItem = items.get(furn.getId());
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setId(furn.getId());
            cartItem.setName(furn.getName());
            cartItem.setPrice(furn.getPrice());
            cartItem.setCount(1);
            cartItem.setPath(furn.getPath());
            items.put(furn.getId(), cartItem);
        } else {
            cartItem.setCount(cartItem.getCount() + 1);
        }
        totalPrice = totalPrice.add(furn.getPrice());
    }

    //cart page modify count
    public void setItemCount(Furn furn, int count) {
        this.totalCount = this.totalCount - items.get(furn.getId()).getCount() + count;
        CartItem cartItem = items.get(furn.getId());
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setId(furn.getId());
            cartItem.setName(furn.getName());
            cartItem.setPrice(furn.getPrice());
            cartItem.setCount(count);
            items.put(furn.getId(), cartItem);
        } else {
            totalPrice = totalPrice.subtract(cartItem.getTotalPrice());
            cartItem.setCount(count);
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }
    }

    //cart page modify item
    public void deleteItem(Furn furn) {
        this.totalCount = this.totalCount - items.get(furn.getId()).getCount();
        CartItem cartItem = items.get(furn.getId());
        if (cartItem.getCount() == 1) {
            items.remove(furn.getId());
        } else {
            cartItem.setCount(cartItem.getCount() - 1);
        }
        totalPrice = totalPrice.subtract(furn.getPrice());
    }

    public void clear() {
        this.totalCount = 0;
        items.clear();
        totalPrice = new BigDecimal(0);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }
}
