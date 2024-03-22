package com.sber.chat.messenger.repositories;

import com.sber.chat.messenger.domains.Notification;

import java.util.Map;

public interface RedisRepository {
    void add(Notification notification);
    void delete(String recipientName,String id);
    Notification findNotification(String recipientName,String id);
    Map<String, Notification> findAllNotifications(String recipientName);

}
