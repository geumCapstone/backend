package com.geum.mvcServer.animal.entity;

import com.geum.mvcServer.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Animal {

    @Id @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User providerId;

    private String name;

    private String race; // 품종

    private int age;

    private LocalDateTime birthday;
}
