package com.sber.chat.messenger.controllers;

import com.sber.chat.messenger.domains.User;

import com.sber.chat.messenger.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class UserController {

    UserService userService;


    private final String FETCH_ADD_USER = "/user.addUser";

    @PostMapping(FETCH_ADD_USER)
    public User addUser(@RequestBody User user) {
        log.info("LOG:saving user...");
        userService.saveUser(user);
        return user;
    }


    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.getAllUsers();
    }



}