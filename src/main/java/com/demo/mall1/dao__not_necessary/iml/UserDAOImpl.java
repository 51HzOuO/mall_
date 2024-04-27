package com.demo.mall1.dao__not_necessary.iml;

import com.demo.mall1.beans.User;
import com.demo.mall1.dao__not_necessary.UserDAO;
import com.demo.mall1.utils.GetQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public User getUserByUsername(String username) {
        try {
            return GetQueryRunner.getQueryRunner().query("select * from users where username = ?", new BeanHandler<>(User.class), username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addUser(User user) {
        try {
            return GetQueryRunner.getQueryRunner().execute("insert into users values(null,?,MD5(?),?)", user.getUsername(), user.getPassword(), user.getEmail()) == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
