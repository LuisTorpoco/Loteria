package com.helloword.loteria.repository;

import com.helloword.loteria.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
