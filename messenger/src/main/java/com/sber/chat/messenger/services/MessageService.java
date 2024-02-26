package com.sber.chat.messenger.services;

import com.sber.chat.messenger.domains.Message;
import com.sber.chat.messenger.repositories.MessageRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class MessageService {

    MessageRepo messageRepo;
    ChatRoomService chatRoomService;

    public Message save(Message message) {

        String chatId = chatRoomService.getChatRoomId(
                message.getSenderId(),
                message.getRecipientId());

        message.setChatId(chatId);
        messageRepo.save(message);
        return message;

    }

    public List<Message> findMessages(
            String senderName,
            String recipientName
    ) {
        String chatId = chatRoomService.getChatRoomId(senderName,recipientName);
        List<Message> list = messageRepo.findAllByChatId(chatId).orElse(new ArrayList<>());
        log.info(String.format("LOG:         list of messages: %s", Arrays.toString(list.toArray())));
        return list;

    }


}
