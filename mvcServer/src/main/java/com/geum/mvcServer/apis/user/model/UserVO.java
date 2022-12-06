package com.geum.mvcServer.apis.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserVO {

    private String username;

    private String password;

    private String email;

    private String nickname;
}
