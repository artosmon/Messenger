package com.sber.chat.messenger.services;

import com.sber.chat.messenger.domains.User;
import com.sber.chat.messenger.repositories.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

    UserRepo userRepo;
    PasswordEncoder passwordEncoder;


    public void saveUser(User user) {
        if(userRepo.findByName(user.getName()).isEmpty()) {
            log.info(String.format("LOG: saveUser name: %s, password: %s",user.getName(),user.getPassword()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        }
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }


}



