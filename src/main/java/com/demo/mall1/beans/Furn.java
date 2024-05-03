package com.demo.mall1.beans;

import java.math.BigDecimal;

public class Furn {
    private int id;
    private String path;
    private String name;
    private String merchant;
    private BigDecimal price;
    private int sales;
    private int total;

    public Furn() {
    }

    public Furn(int id, String path, String name, String merchant, BigDecimal price, int sales, int total) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.merchant = merchant;
        this.price = price;
        this.sales = sales;
        this.total = total;
    }

    public Furn(String path, String name, String merchant, BigDecimal price, int sales, int total) {
        this.path = path;
        this.name = name;
        this.merchant = merchant;
        this.price = price;
        this.sales = sales;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
