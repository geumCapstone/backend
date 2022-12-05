package com.geum.mvcServer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserVO {

    private String userId;

    private String password;

    private String email;

    private String nickname;
}
