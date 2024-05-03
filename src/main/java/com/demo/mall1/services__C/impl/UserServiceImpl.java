package com.demo.mall1.services__C.impl;

import com.demo.mall1.beans.Info;
import com.demo.mall1.beans.User;
import com.demo.mall1.services__C.UserService;
import com.demo.mall1.utils.GetQueryRunner;
import com.demo.mall1.web__V.LoginServlet;
import com.demo.mall1.web__V.template.UserServlet;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    @Override
    public boolean register(Info info) {
        if (!verifyInfo(info)) {
            LoginServlet.returnInfo.set("InvalidInput");
            return false;
        }
        try {
            if (getUserByUsername(info.getUsername()) != null) {
                LoginServlet.returnInfo.set("AlreadyExists");
                return false;
            } else {
                GetQueryRunner.getQueryRunner().execute("insert into users values(null,?,MD5(?),?,0)", info.getUsername(), info.getPassword(), info.getEmail());
            }
            LoginServlet.returnInfo.set(getUserByUsername(info.getUsername()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private boolean verifyInfo(Info info) {
        String regU = "^\\w{4,16}$";
        String regP = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]+$";
        String regE = "^[A-Za-z0-9一-龥]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$";
        String code = info.getCode();
        return info.getUsername().matches(regU) && info.getPassword().matches(regP) && info.getEmail().matches(regE) && code.matches("^[0-9]{4}$");
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        return GetQueryRunner.getQueryRunner().query("select * from users where username = ?", new BeanHandler<>(User.class), username);
    }

    private boolean checkPassword(String password, String md5Str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(password.getBytes());
        byte[] digest = md5.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString().equals(md5Str);
    }

    public boolean login(String username, String password) {
        User user = null;
        try {
            user = getUserByUsername(username);
            if (user == null) {
                UserServlet.returnInfo.set("UserNotFound");
                return false;
            } else if (!checkPassword(password, user.getPassword())) {
                UserServlet.returnInfo.set("WrongPassword");
                return false;
            } else if (user.getType() == 0) {
                UserServlet.returnInfo.set("NotAdmin");
                return false;
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        UserServlet.returnInfo.set(user);
        return true;
    }


    @Override
    public boolean login(User user) {
        User userByUsername = null;
        try {
            userByUsername = getUserByUsername(user.getUsername());
            if (userByUsername == null) {
                LoginServlet.returnInfo.set("UserNotFound");
                return false;
            } else if (!checkPassword(user.getPassword(), userByUsername.getPassword())) {
                LoginServlet.returnInfo.set("WrongPassword");
                return false;
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        LoginServlet.returnInfo.set(userByUsername);

        return true;
    }
}
