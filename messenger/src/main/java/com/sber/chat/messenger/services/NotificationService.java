package com.sber.chat.messenger.services;


import com.sber.chat.messenger.domains.Notification;
import com.sber.chat.messenger.repositories.RedisRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class NotificationService {

    RedisRepository redisRepository;
    public void save(Notification notification) {
        redisRepository.add(notification);
    }

    public void remove(String recipientName,String id) {
        redisRepository.delete(recipientName,id);
    }

    public Notification findNotificationById(String recipientName,String id) {
       return redisRepository.findNotification(recipientName,id);
    }

    public List<Notification> findAllUserNotificationsByRecipientId(String recipientName) {
        Map<String, Notification> aa = redisRepository.findAllNotifications(recipientName);
        List<Notification> list = new ArrayList<Notification>();
        for(Map.Entry<String, Notification> entry : aa.entrySet()){
            String key = (String) entry.getKey();
            list.add((Notification) aa.get(key));
        }
        return Optional.of(list).orElseGet(ArrayList::new);
    }

}
