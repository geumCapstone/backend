package com.geum.mvcServer.apis.user.controller;

import com.geum.mvcServer.apis.user.entity.User;
import com.geum.mvcServer.apis.user.model.UserVO;
import com.geum.mvcServer.apis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("v1/users/register")
    public ResponseEntity<User> registerUser(@RequestBody UserVO userVO) {
        userVO.setPassword(bCryptPasswordEncoder.encode(userVO.getPassword()));
        User user = userService.registerUser(userVO);

        if (user.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("v1/users/edit")
    public ResponseEntity<User> editUser(@RequestBody UserVO userVO, HttpServletRequest request) {
        User user = userService.editUserData(userVO, request);

        if(user == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("v1/users/out")
    public ResponseEntity<User> expireUser(HttpServletRequest request) {
        User user = userService.outOfService(request);

        if(user.getRole() != null) {
            return ResponseEntity.internalServerError().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

}
