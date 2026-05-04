package com.helloword.loteria.controller;

import com.helloword.loteria.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserController {
    ResponseEntity<List<User>> users();
    ResponseEntity<User> findUserById(String id);
    ResponseEntity<Void> registerUser(String id, User user);
    ResponseEntity<String> addApuesta(String id, List<Integer> apuesta);
}
