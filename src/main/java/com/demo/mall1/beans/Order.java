package com.demo.mall1.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private final String ID;
    private Date creatTime;
    private BigDecimal price;
    private Integer status;
    private Integer userID;
    private List<OrderItem> orderItems;

    public Order(Date creatTime, BigDecimal price, Integer status, Integer userID, List<OrderItem> orderItems) {
        this();
        this.creatTime = creatTime;
        this.price = price;
        this.status = status;
        this.userID = userID;
        this.orderItems = orderItems;
    }

    public Order() {
        this.ID = System.currentTimeMillis() + "" + (int) (Math.random() * 100);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
