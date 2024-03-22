package com.sber.chat.messenger.domains;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification implements Serializable {

    String id;
    String senderId;
    String recipientId;
}
