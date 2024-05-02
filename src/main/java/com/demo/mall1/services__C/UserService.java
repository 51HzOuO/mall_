package com.demo.mall1.services__C;

import com.demo.mall1.beans.Info;
import com.demo.mall1.beans.User;

import java.sql.SQLException;

public interface UserService {
    boolean register(Info info);

    User getUserByUsername(String username) throws SQLException;

    boolean login(User user);

    boolean login(String username, String password);
}
