package com.sber.chat.messenger.services;

import com.sber.chat.messenger.domains.Message;
import com.sber.chat.messenger.dto.MessageDto;
import com.sber.chat.messenger.repositories.MessageRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class MessageService {

    MessageRepo messageRepo;
    ChatRoomService chatRoomService;
    UserService userService;
    public Message save(MessageDto messageDto) {


        long chatId = chatRoomService.getChatRoomId(
                messageDto.getSenderId(),
                messageDto.getRecipientId());

        Message message = Message.builder()
                .chat(chatRoomService.getChatRoomById(chatId))
                .sender(userService.getUserByName(messageDto.getSenderId()))
                .recipient(userService.getUserByName(messageDto.getRecipientId()))
                .content(messageDto.getContent())
                .build();

        messageDto.setChatId(chatId);

        messageRepo.save(message);
        return message;

    }

    public List<MessageDto> findMessages(
            String senderName,
            String recipientName
    ) {
        long chatId = chatRoomService.getChatRoomId(senderName,recipientName);
        List<Message> list = messageRepo.findAllByChatId(chatId).orElse(new ArrayList<>());

        log.info(String.format("LOG: list of messages: %s", Arrays.toString(list.toArray())));

        return list.stream().map(m -> MessageDto.builder()
        .chatId(m.getChat()
                .getId())
                .senderId(m.getSender().getName())
                .recipientId(m.getRecipient().getName())
                .content(m.getContent())
                .time(m.getTime())
                .build()).collect(Collectors.toList());
    }


}
