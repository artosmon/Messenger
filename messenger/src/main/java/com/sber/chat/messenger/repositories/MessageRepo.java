package com.sber.chat.messenger.repositories;

import com.sber.chat.messenger.domains.Message;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message,Long> {

    Optional<List<Message>> findAllByChatId(long id);
}
