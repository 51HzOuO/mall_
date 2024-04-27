package com.demo.mall1.services__C;

import com.demo.mall1.beans.RegisterInfo;
import com.demo.mall1.beans.User;

import java.sql.SQLException;

public interface UserService {
    boolean register(RegisterInfo registerInfo);

    User getUserByUsername(String username) throws SQLException;
}
