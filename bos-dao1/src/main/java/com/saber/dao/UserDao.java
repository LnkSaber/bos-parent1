package com.saber.dao;

import com.saber.domain.User;

public interface UserDao extends BaseDao<User> {

    User finUserBy(User model);

    void executeUpdate(String id, String password);

    User finUserByUsername(String username);
}
