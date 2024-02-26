package com.sber.chat.messenger.services;

import com.sber.chat.messenger.domains.User;
//import com.sber.chat.messenger.factories.UserDtoFactory;
import com.sber.chat.messenger.repositories.ChatRoomRepo;
import com.sber.chat.messenger.repositories.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

//    SimpMessagingTemplate messagingTemplate;
    UserRepo userRepo;



    public void saveUser(User user) {
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }




    }



