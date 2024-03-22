package com.sber.chat.messenger.repositories;

import com.sber.chat.messenger.domains.Notification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Repository
public class RedisRepositoryImpl implements RedisRepository {
    static String KEY = "messenger:notification:recipient";

    HashOperations<String,String,Notification> hashOperations;
    @Override
    public void add(Notification notification) {
        String id = notification.getSenderId()+"_"+notification.getRecipientId();
        hashOperations.put(KeyHelper.makeKey(notification.getRecipientId()),id,notification);
    }

    @Override
    public void delete(String recipientName,String id) {
        hashOperations.delete(KeyHelper.makeKey(recipientName),id);
    }

    @Override
    public Notification findNotification(String recipientName,String id) {
        return (Notification) hashOperations.get(KeyHelper.makeKey(recipientName), id);
    }

    @Override
    public Map<String, Notification> findAllNotifications(String recipientName) {
        return hashOperations.entries(KeyHelper.makeKey(recipientName));
    }


    private static class KeyHelper {

        static String KEY = "messenger:notification:user:{recipient_name}";

        public static String makeKey(String recipientName) {
            return KEY.replace("{recipient_name}", recipientName);
        }
    }

}


