package com.demo.mall1.beans;

import java.math.BigDecimal;

public class OrderItem {
    private String OrderID;
    private String name;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;

    public OrderItem() {
    }

    public OrderItem(String orderID, String name, BigDecimal price, Integer count, BigDecimal totalPrice) {
        this.OrderID = orderID;
        this.name = name;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
