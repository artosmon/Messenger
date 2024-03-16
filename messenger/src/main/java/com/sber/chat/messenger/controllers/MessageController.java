package com.sber.chat.messenger.controllers;

import com.sber.chat.messenger.dto.MessageDto;
import com.sber.chat.messenger.services.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Controller
public class MessageController {

    SimpMessagingTemplate simpMessagingTemplate;
    MessageService messageService;

    private final String FETCH_SEND_MESSAGE_USER = "/topic/messages.users.{username}";
    private final String FETCH_ALL_MESSAGE_FROM_CHAT = "/messages/{senderName}/{recipientName}";
    private final String FETCH_SEND_MESSAGE_CHAT = "/send.message";



    @MessageMapping(FETCH_SEND_MESSAGE_CHAT)
    public void processMessage(@Payload MessageDto messageDto) {

        messageService.save(messageDto);
        log.info(String.format("LOG:processMessage: %s",messageDto.getContent()));

        simpMessagingTemplate.convertAndSend(
                getRout(FETCH_SEND_MESSAGE_USER, messageDto.getRecipientId()),
                messageDto
        );
    }

    @GetMapping(FETCH_ALL_MESSAGE_FROM_CHAT)
    public ResponseEntity<List<MessageDto>> findChatMessage(
            @PathVariable("senderName") String senderName,
            @PathVariable("recipientName") String recipientName
    ) {

        return  ResponseEntity.ok(messageService.findMessages(senderName,recipientName));
    }


    private String getRout(String url,String str) {
        return url.replace("{username}", str);
    }
}