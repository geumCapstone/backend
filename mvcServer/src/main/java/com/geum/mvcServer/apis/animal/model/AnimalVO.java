package com.geum.mvcServer.apis.animal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geum.mvcServer.apis.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AnimalVO {

    private User userId;

    private String name;

    private String race; // 품종

    private int age;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime birthday;

    public boolean isEmpty() {
        return userId == null || name == null || race == null || birthday == null;
    }

}
