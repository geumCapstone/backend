package com.geum.mvcServer.apis.user.controller;

import com.geum.mvcServer.apis.user.entity.User;
import com.geum.mvcServer.apis.user.model.UserVO;
import com.geum.mvcServer.apis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
    @RequestMapping("v1/users/{providerId}")
    public ResponseEntity<User> idCheck(@PathVariable("providerId") String providerId) {
        User userData = userService.idCheck(providerId);
        if(userData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // return ResponseEntity.ok().body(userRepository.findByProviderId(providerId));
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }
     */

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
