package com.geum.mvcServer.apis.user.service;

import com.geum.mvcServer.apis.user.entity.User;
import com.geum.mvcServer.apis.user.entity.UserRepository;
import com.geum.mvcServer.apis.user.model.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /*
    public User idCheck(String providerId) {
        User data = userRepository.findByProviderId(providerId);

        if(data.isEmpty()) {
            return null;
        } else {
            return data;
        }
    }
     */

    public User registerUser(UserVO userVO) {
        User user = new User();

        user.setUsername(userVO.getUsername());
        user.setPassword(passwordEncoder.encode(userVO.getPassword()));
        user.setNickname(userVO.getNickname());
        user.setEmail(userVO.getEmail());
        user.setRole("ROLE_USER");

        User saveUser = userRepository.save(user);

        if (saveUser.isEmpty()) {
            return null;
        } else {
            return saveUser;
        }
    }

    /** 해야할 것 -> 유저 수정, 탈퇴 등...?*/


}
