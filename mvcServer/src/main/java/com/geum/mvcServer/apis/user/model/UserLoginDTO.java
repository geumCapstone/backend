package com.geum.mvcServer.apis.user.model;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String username;
    private String password;
}
