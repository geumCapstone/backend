package com.geum.mvcServer.user.entity;


import com.geum.mvcServer.animal.entity.Animal;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "user_data")
public class User {

    @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String providerId; // SNS 인증 후 나타나는 고유 번호

    private String nickname;

    @OneToMany(mappedBy = "providerId")
    private List<Animal> animals = new ArrayList<>();

    public boolean isEmpty() {
        return id == null || providerId == null || nickname == null;
    }
}
