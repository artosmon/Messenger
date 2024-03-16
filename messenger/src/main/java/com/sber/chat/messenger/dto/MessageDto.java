package com.sber.chat.messenger.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    Long chatId;
    String senderId; // sender's name
    String recipientId; // recipient's name
    String content;


    Instant time;
}
