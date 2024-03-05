package com.sber.chat.messenger.controllers;

import com.sber.chat.messenger.domains.User;

import com.sber.chat.messenger.security.MyUserDetailsService;
import com.sber.chat.messenger.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class UserController {

    UserService userService;


    private final String FETCH_ADD_USER = "/user.addUser";

    @PostMapping(FETCH_ADD_USER)
    @ResponseBody
    public User addUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/username")
    public ResponseEntity<User> sendUsername() {
        log.info("     LOG username:"+MyUserDetailsService.sentUsername.getName());
        return ResponseEntity.ok(MyUserDetailsService.sentUsername);
    }

}