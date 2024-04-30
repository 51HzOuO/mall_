package com.demo.mall1.beans;

public class Info {
    private String username;
    private String password;
    private String email;
    private String code;

    public Info(String username, String password, String email, String number) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.code = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
