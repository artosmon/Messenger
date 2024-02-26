package com.sber.chat.messenger.services;

import com.sber.chat.messenger.domains.ChatRoom;
import com.sber.chat.messenger.repositories.ChatRoomRepo;
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
    public String getChatRoomId(String senderName,String recipientName) {

       return chatRoomRepo.findBySenderNameAndRecipientName(senderName, recipientName)
                .map(ChatRoom::getChatId)
                .or( () ->
                        {
                            return Optional.of(createChat(senderName,recipientName));
                        }
                ).get();

    }

    private String createChat(String senderId, String recipientId) {
        log.info("LOG:         I create chat!");
        ChatRoom sender = ChatRoom.builder()
                .senderName(senderId)
                .recipientName(recipientId)
                .build();

//        ChatRoom recipient = ChatRoom.builder()
//                .senderName(recipientId)
//                .recipientName(senderId)
//                .build();
        chatRoomRepo.save(sender);
//        chatRoomRepo.save(recipient);
        return sender.getChatId();
    }

}
