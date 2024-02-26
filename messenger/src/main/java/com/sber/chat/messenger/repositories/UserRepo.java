package com.sber.chat.messenger.repositories;

import com.sber.chat.messenger.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User,String> {
}
