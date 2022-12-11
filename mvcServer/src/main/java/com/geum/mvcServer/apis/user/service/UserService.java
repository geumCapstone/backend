package com.geum.mvcServer.apis.user.service;

import com.geum.mvcServer.apis.user.entity.User;
import com.geum.mvcServer.apis.user.entity.UserRepository;
import com.geum.mvcServer.apis.user.model.UserVO;
import com.geum.mvcServer.config.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;

    public User registerUser(UserVO userVO) {
        User user = new User();

        user.setUsername(userVO.getUsername());
        user.setPassword(passwordEncoder.encode(userVO.getPassword()));
        user.setNickname(userVO.getNickname());
        user.setEmail(userVO.getEmail());
        user.setRole("ROLE_USER");

        User saveUser = userRepository.save(user);

        return saveUser;
    }

    public User editUserData(UserVO userVO, HttpServletRequest request) {
        User user = (User) sessionManager.getSession(request);

        User editUser = null;

        if(!user.getUsername().equals(userVO.getUsername())) {
            return editUser;
        } else {
            user.setPassword(userVO.getPassword());
            user.setNickname(userVO.getNickname());
            user.setEmail(userVO.getEmail());

            if(user.getPassword().equals(userVO.getPassword())) {
                user.setOldPassword(user.getPassword());
            }

            editUser = userRepository.save(user);

            return editUser;
        }
    }

    public User outOfService(HttpServletRequest request) {
        User user = (User) sessionManager.getSession(request);

        user.setRole(null);
        userRepository.save(user);

        sessionManager.expire(request);

        return user;
    }


}
