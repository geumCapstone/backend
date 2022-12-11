package com.geum.mvcServer.apis.user.login.service;

import com.geum.mvcServer.apis.user.entity.User;
import com.geum.mvcServer.apis.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);

        return user;
    }
}
