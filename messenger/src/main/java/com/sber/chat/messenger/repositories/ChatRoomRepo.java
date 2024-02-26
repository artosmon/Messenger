package com.sber.chat.messenger.repositories;

import com.sber.chat.messenger.domains.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ChatRoomRepo extends JpaRepository<ChatRoom,String> {

    @Query("from ChatRoom where senderName in(?1 , ?2)  and  recipientName in (?1 ,?2)")
    Optional<ChatRoom> findBySenderNameAndRecipientName(String senderId, String recipientId);
}
