package com.helloword.loteria.controller;

import com.helloword.loteria.entity.User;
import com.helloword.loteria.repository.UserRepository;
import com.helloword.loteria.services.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loteria/users")
public class UserControllerImpl implements UserController {

    private final LotteryService lotteryService;

    public UserControllerImpl(LotteryService lotteryService, UserRepository userRepository) {
        this.lotteryService = lotteryService;
    }


    @Override
    @GetMapping
    public ResponseEntity<List<User>> users() {
        return new ResponseEntity<>(lotteryService.findAllUsers(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable String id) {
         return new ResponseEntity<>(lotteryService.findUserById(id), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Void> registerUser(@PathVariable String id, @RequestBody User user) {
        user.setId(id);
        lotteryService.registerUsers(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/{id}/apuesta")
    public ResponseEntity<String> addApuesta(@PathVariable String id,@RequestBody List<Integer> apuesta) {
        lotteryService.addApuestaToUser(id, apuesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
