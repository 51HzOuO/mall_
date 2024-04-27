package com.demo.mall1.services__C;

import com.demo.mall1.beans.RegisterInfo;
import com.demo.mall1.beans.User;
import com.demo.mall1.utils.GetQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    @Override
    public boolean register(RegisterInfo registerInfo) {
        try {
            if (getUserByUsername(registerInfo.getUsername()) != null) {
                return false;
            } else {
                GetQueryRunner.getQueryRunner().execute("insert into users values(null,?,MD5(?),?)", registerInfo.getUsername(), registerInfo.getPassword(), registerInfo.getEmail());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        return GetQueryRunner.getQueryRunner().query("select * from users where username = ?", new BeanHandler<>(User.class), username);
    }
}
