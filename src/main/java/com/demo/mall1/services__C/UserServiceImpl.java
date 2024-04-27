package com.demo.mall1.services__C;

import com.demo.mall1.beans.RegisterInfo;
import com.demo.mall1.beans.User;
import com.demo.mall1.utils.GetQueryRunner;
import com.demo.mall1.web__V.RegisterServlet;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    @Override
    public boolean register(RegisterInfo registerInfo) {
        if (!verifyLogin(registerInfo)) {
            RegisterServlet.returnInfo.set("InvalidInput");
            return false;
        }
        try {
            if (getUserByUsername(registerInfo.getUsername()) != null) {
                RegisterServlet.returnInfo.set("AlreadyExists");
                return false;
            } else {
                GetQueryRunner.getQueryRunner().execute("insert into users values(null,?,MD5(?),?)", registerInfo.getUsername(), registerInfo.getPassword(), registerInfo.getEmail());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private boolean verifyLogin(RegisterInfo registerInfo) {
        String regU = "^\\w{4,16}$";
        String regP = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]";
        String regE = "^(([^<>()[\\]\\\\.,;:\\s@\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        String code = registerInfo.getCode();
        return registerInfo.getUsername().matches(regU) && registerInfo.getPassword().matches(regP) && registerInfo.getEmail().matches(regE) && code.matches("^[0-9]{4}$");
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        return GetQueryRunner.getQueryRunner().query("select * from users where username = ?", new BeanHandler<>(User.class), username);
    }
}
