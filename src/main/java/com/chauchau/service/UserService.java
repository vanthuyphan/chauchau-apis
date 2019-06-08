package com.chauchau.service;

import com.chauchau.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();
    User createUser(User user);
    User findUserbyPhone(String phone);
    int updateUser(User user);
    int delete(User user);
    void sendMail(String email);
}
