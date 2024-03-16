package com.sber.chat.messenger.services;

import com.sber.chat.messenger.domains.ChatRoom;
import com.sber.chat.messenger.domains.User;
import com.sber.chat.messenger.repositories.ChatRoomRepo;
import com.sber.chat.messenger.repositories.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ChatRoomService {

    ChatRoomRepo chatRoomRepo;
    UserService userService;
    public long getChatRoomId(String firstName,String secondName) {

        return chatRoomRepo.findByUserFirstNameAndUserSecondName(firstName, secondName)
                .map(ChatRoom::getId)
                .or( () ->
                        {
                            return Optional.of(createChat(firstName,secondName));
                        }
                ).get();

    }

    private long createChat(String firstUserName, String secondUserName) {
        ChatRoom chat = ChatRoom.builder()
                .userFirst(userService.getUserByName(firstUserName))
                .userSecond(userService.getUserByName(secondUserName))
                .build();
        chatRoomRepo.save(chat);
        log.info(String.format("LOG: create chat: %s",chat.getId()));
        return chat.getId();
    }

    public ChatRoom getChatRoomById(long id) {
        return chatRoomRepo.findById(id).orElseThrow(RuntimeException::new);
    }

}
