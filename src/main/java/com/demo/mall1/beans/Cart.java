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
    public int setItemCount(Furn furn, int count) {
        int ret;
        CartItem cartItem = items.get(furn.getId());
        if (cartItem == null) {
            this.totalCount = this.totalCount + count;
            cartItem = new CartItem();
            cartItem.setId(furn.getId());
            cartItem.setName(furn.getName());
            cartItem.setPrice(furn.getPrice());
            cartItem.setCount(count);
            items.put(furn.getId(), cartItem);
            ret = 0;
        } else {
            ret = cartItem.getCount();
            this.totalCount = this.totalCount - items.get(furn.getId()).getCount() + count;
            totalPrice = totalPrice.subtract(cartItem.getTotalPrice());
            cartItem.setCount(count);
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }
        return ret;
    }

    //cart page modify item
    public void deleteItem(Furn furn) {
        this.totalCount = this.totalCount - items.get(furn.getId()).getCount();
        this.totalPrice = totalPrice.subtract(items.get(furn.getId()).getTotalPrice());
        items.remove(furn.getId());
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
