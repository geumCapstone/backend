package com.geum.mvcServer.user.controller;

import com.geum.mvcServer.user.entity.User;
import com.geum.mvcServer.user.entity.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("v1/users/{providerId}")
    public ResponseEntity<User> idCheck(@PathVariable("providerId") String providerId) {
        if(userRepository.findByProviderId(providerId) == null) {
            return ResponseEntity.notFound().build();
        }

        // return ResponseEntity.ok().body(userRepository.findByProviderId(providerId));
        return new ResponseEntity<>(userRepository.findByProviderId(providerId), HttpStatus.OK);
    }


    /** 제네릭 문법을 통한 ResponseEntity 데이터 전달 효율 증가 */
    @Getter @Setter
    static class Result<T> {
        private T data;

        public Result(T data) {
            this.data = data;
        }
    }
}
