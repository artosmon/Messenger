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
@Table(name = "users")
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    Long id;

    String name;

//    Status status;

//    public enum Status {
//        ONLINE,
//        OFFLINE
//    }


//    String password;

}
