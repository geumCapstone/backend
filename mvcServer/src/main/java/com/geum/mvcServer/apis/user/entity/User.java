package com.geum.mvcServer.apis.user.entity;


import com.geum.mvcServer.apis.animal.entity.Animal;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "user_data",
        indexes = @Index(name = "username", columnList = "username"))
public class User {

    @Id @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Column(unique = true)
    private String username;

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

    // private Timestamp loginDate;

    @NotNull
    private String role; // ROLE_USER, ROLE_ADMIN

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Animal> animals = new ArrayList<>();

    public boolean isEmpty() {
        return username != null || password != null || email != null || nickname != null || role != null;
    }

}
