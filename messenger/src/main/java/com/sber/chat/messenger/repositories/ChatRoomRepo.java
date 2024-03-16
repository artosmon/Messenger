package com.sber.chat.messenger.repositories;

import com.sber.chat.messenger.domains.ChatRoom;
import com.sber.chat.messenger.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ChatRoomRepo extends JpaRepository<ChatRoom,Long> {

    @Query("from ChatRoom where userFirst.name in(?1 , ?2)  " +
            "and  userSecond.name in (?1 ,?2)")
    Optional<ChatRoom> findByUserFirstNameAndUserSecondName(String firstUser, String secondUser);
}
