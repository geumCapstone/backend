package com.geum.mvcServer.user.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class User {

    @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String providerId; // SNS 인증 후 나타나는 고유 번호

    private String nickname;
}
