package com.smarthostel.service;

import com.smarthostel.dao.UserDao;
import com.smarthostel.model.User;

import java.util.Optional;

public class AuthService {

    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> login(String email, String password) {
        return userDao.authenticate(email, password);
    }
}
