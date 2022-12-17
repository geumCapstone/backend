package com.geum.mvcServer.apis.animal.entity;

import com.geum.mvcServer.apis.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @JoinColumn(name = "userId")
    private User userId;

    private String name;

    private String race; // 품종

    private int age;

    private LocalDateTime birthday;
}
