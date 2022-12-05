package com.geum.mvcServer.user.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
@Table(name = "user_data", indexes = @Index(name = "usrId", columnList = "userId"))
public class User {

    @Id @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Column(unique = true)
    private String userId;

    @NotNull
    private String password;

    @Column(nullable = true)
    private String oldPassword;

    @NotNull @Column(unique = true)
    private String email;

    @NotNull
    private String nickname;

    @CreationTimestamp
    private Timestamp regDate;

    private String role; // ROLE_USER, ROLE_ADMIN

    public boolean isEmpty() {
        return userId != null || password != null || email != null || nickname != null || role != null;
    }

}
