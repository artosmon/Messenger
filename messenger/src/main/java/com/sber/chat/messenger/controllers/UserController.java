package com.sber.chat.messenger.controllers;

import com.sber.chat.messenger.domains.User;

import com.sber.chat.messenger.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class UserController {

    UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/topic.addUser")
    public User addUser(@Payload User user) {
        userService.saveUser(user);
        return user;
    }

//    @MessageMapping("/user.disconnectUser")
//    @SendTo("/topic.disconnectUser")
//    public User disconnect(@Payload User user) {
//        userService.disconnect(user);
//        return user;
//    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}