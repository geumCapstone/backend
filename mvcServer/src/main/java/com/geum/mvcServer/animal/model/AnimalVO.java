package com.geum.mvcServer.animal.model;

import com.geum.mvcServer.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AnimalVO {

    private User providerId;

    private String name;

    private String race; // 품종

    private int age;

    private LocalDateTime birthday;

    public boolean isEmpty() {
        return providerId == null || name == null || race == null || birthday == null;
    }

}
