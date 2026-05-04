package com.helloword.loteria.services;

import com.helloword.loteria.entity.User;

import java.util.List;

public interface LotteryService {
    void resgisterUsers(User u);
    List<User> findAllUsers();
    User findUserById(String id);
    void addApuestaToUser(String id, List<Integer> apuesta);
}
