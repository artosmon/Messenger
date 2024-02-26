package com.sber.chat.messenger.domains;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "chatroom")
public class ChatRoom {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    long id;
@Builder.Default
    String chatId = UUID.randomUUID().toString().substring(0,4);
//    String name;
    String senderName;
    String recipientName;

}
