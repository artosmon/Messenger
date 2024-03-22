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
    private final String FETCH_GET_LOGGED_USERS ="/users.loggedUsers";
    private final String FETCH_GET_ALL_USERS = "/users";

    @PostMapping(FETCH_ADD_USER)
    public void addUser(@RequestBody User user) {
        log.info("LOG:saving user...");
        userService.saveUser(user);
    }

    @GetMapping(FETCH_GET_ALL_USERS)
    public List<User> findAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping(FETCH_GET_LOGGED_USERS)
    public List<String > getLoggedUsers() {
        return userService.getUsersOnline();
    }



}