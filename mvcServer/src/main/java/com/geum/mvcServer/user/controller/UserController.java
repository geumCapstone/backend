package com.geum.mvcServer.user.controller;

import com.geum.mvcServer.user.entity.User;
import com.geum.mvcServer.user.model.UserVO;
import com.geum.mvcServer.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("v1/users/{providerId}")
    public ResponseEntity<User> idCheck(@PathVariable("providerId") String providerId) {
        User userData = userService.idCheck(providerId);
        if(userData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // return ResponseEntity.ok().body(userRepository.findByProviderId(providerId));
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @PostMapping("v1/users/register")
    public ResponseEntity<User> registerUser(@RequestBody UserVO userVO) {
        User user = userService.registerUser(userVO);

        if (user.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        } else {
            return ResponseEntity.ok().body(user);
        }
    }

}
