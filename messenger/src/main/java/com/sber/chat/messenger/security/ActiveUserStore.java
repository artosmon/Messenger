package com.sber.chat.messenger.security;

import com.sber.chat.messenger.domains.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActiveUserStore {

    List<String> users;
    List<String> twiceLoggedUsers;


    public ActiveUserStore() {
        users = new ArrayList<String>();
        twiceLoggedUsers = new LinkedList<>();
    }

    public void addActiveUser(String user) {
        if(!users.contains(user)) {
            users.add(user);
        }
        else {
            twiceLoggedUsers.add(user);
        }
    }

    public void inactiveUser(String user) {

        if(!twiceLoggedUsers.remove(user)) {
            users.remove(user);
        }

    }

}