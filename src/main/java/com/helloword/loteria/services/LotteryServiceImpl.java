package com.helloword.loteria.services;

import com.helloword.loteria.entity.User;
import com.helloword.loteria.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryServiceImpl implements LotteryService {

    private final UserRepository userRepository;

    public LotteryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void resgisterUsers(User u) {

    }

    @Override
    public List<User> findAllUsers() {
        return List.of();
    }

    @Override
    public User findUserById(String id) {
        return null;
    }

    @Override
    public void addApuestaToUser(String id, List<Integer> apuesta) {

    }
}
