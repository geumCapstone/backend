package com.geum.mvcServer.user.entity;


import com.geum.mvcServer.animal.entity.Animal;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "userData")
public class User {

    @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String providerId; // SNS 인증 후 나타나는 고유 번호

    private String nickname;

    @OneToMany(mappedBy = "providerId")
    private List<Animal> animals = new ArrayList<>();
}
