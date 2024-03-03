package com.sber.chat.messenger.repositories;

import com.sber.chat.messenger.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByName(String username);
}
