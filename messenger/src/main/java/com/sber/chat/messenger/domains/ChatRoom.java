package com.sber.chat.messenger.domains;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "chatroom")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_user_name",referencedColumnName="name")
    User userFirst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_user_name",referencedColumnName="name")
    User userSecond;

}
