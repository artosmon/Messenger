package com.sber.chat.messenger.controllers;

import com.sber.chat.messenger.domains.Message;
import com.sber.chat.messenger.services.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Controller
public class MessageController {

    SimpMessagingTemplate simpMessagingTemplate;
    MessageService messageService;

    private final String FETCH_SEND_MESSAGE_USER = "/topic/messages/users.{username}";

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        messageService.save(message);
        log.info(String.format("LOG:    processMessage: %s",message.getRecipientId()));

        simpMessagingTemplate.convertAndSend(
                getRout(FETCH_SEND_MESSAGE_USER, message.getRecipientId()),
                Message.builder()
                        .chatId(message.getChatId())
                        .senderId(message.getSenderId())
                        .recipientId(message.getRecipientId())
                        .content(message.getContent())
                        .timestamp(message.getTimestamp())
                        .build()
        );
    }

    @GetMapping("/messages/{senderName}/{recipientName}")
//    @ResponseBody
    public ResponseEntity<List<Message>> findChatMessage(
            @PathVariable("senderName") String senderName,
            @PathVariable("recipientName") String recipientName
    ) {

        return  ResponseEntity.ok(messageService.findMessages(senderName,recipientName));
    }


    private String getRout(String url,String str) {
        return url.replace("{username}", str);
    }
}