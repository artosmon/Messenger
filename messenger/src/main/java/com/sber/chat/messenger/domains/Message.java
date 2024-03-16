package com.sber.chat.messenger.domains;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    ChatRoom chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_name",referencedColumnName="name")
    User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_name",referencedColumnName="name")
    User recipient;

    String content;

    @Builder.Default
    Instant time = Instant.now();

}
