package com.demo.mall1.dao__not_necessary;

import com.demo.mall1.beans.User;

public interface UserDAO {
    User getUserByUsername(String username);

    boolean addUser(User user);
}
