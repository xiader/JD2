package com.gmail.sasha.dao;

import com.gmail.sasha.dao.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {

    User save(Connection connection, User user);

    User findUserByEmail(Connection connection, String email);

    List<User> findAll(Connection connection);
}
