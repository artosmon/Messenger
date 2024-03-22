package com.sber.chat.messenger.services;

import com.sber.chat.messenger.domains.User;
import com.sber.chat.messenger.repositories.UserRepo;
import com.sber.chat.messenger.security.ActiveUserStore;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

    UserRepo userRepo;
    PasswordEncoder passwordEncoder;
    ActiveUserStore activeUserStore;


    public void saveUser(User user) {
        if(userRepo.findByName(user.getName()).isEmpty()) {
            log.info(String.format("LOG: saveUser name: %s, password: %s",user.getName(),user.getPassword()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        }
        else {
            log.info(String.format("LOG: User: %s not saved",user.getName()));
        }
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserByName(String name) {
        return userRepo.findByName(name).orElseThrow(RuntimeException::new);
    }

    public List<String> getUsersOnline() {
        return activeUserStore.getUsers();
    }

    public void addUserOnline(String user) {
        activeUserStore.addActiveUser(user);
    }

    @EventListener
    public void handleConnect(SessionConnectEvent event) {

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        addUserOnline(Objects.requireNonNull(event.getUser()).getName());
        log.info("LOG: connect session, user {}",Objects.requireNonNull(headerAccessor.getUser()).getName());
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        log.info("LOG: disconnect username: {}", Objects.requireNonNull(headerAccessor.getUser()).getName());
        activeUserStore.inactiveUser(Objects.requireNonNull(headerAccessor.getUser()).getName());

    }




}



