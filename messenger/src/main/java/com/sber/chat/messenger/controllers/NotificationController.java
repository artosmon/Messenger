package com.sber.chat.messenger.controllers;


import com.sber.chat.messenger.domains.Notification;
import com.sber.chat.messenger.security.MyUserDetails;
import com.sber.chat.messenger.services.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Controller
public class NotificationController {

    NotificationService notificationService;
    private final String FETCH_NOTIFICATIONS_USER = "/user.getAllNotifications";
    private final String FETCH_GET_NOTIFICATION_USER = "/user.saveNotification";
    private final String FETCH_DELETE_NOTIFICATION_USER = "/user.deleteNotification";


    @GetMapping(FETCH_NOTIFICATIONS_USER)
    public ResponseEntity<List<Notification>> getAllNotifications(
            @AuthenticationPrincipal MyUserDetails myUserDetails) {
        log.info("LOG: user:{}, notifications : {}",myUserDetails.getUsername(),notificationService
                .findAllUserNotificationsByRecipientId(myUserDetails.getUsername()));
        return ResponseEntity.ok(notificationService
                .findAllUserNotificationsByRecipientId(myUserDetails.getUsername()));

    }

    @MessageMapping(FETCH_GET_NOTIFICATION_USER)
    public void saveNotification( @Payload Notification notification) {
        log.info("LOG: notification save: sender_name: {}, recipient_name: {}", notification.getSenderId(),notification.getRecipientId());
        notificationService.save(notification);
    }

    @MessageMapping(FETCH_DELETE_NOTIFICATION_USER)
    public void deleteNotification( @Payload Notification notification) {
        notificationService.remove(notification.getRecipientId(),notification.getId());
    }

}
