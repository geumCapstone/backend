package com.geum.mvcServer.user.service;

import com.geum.mvcServer.user.entity.User;
import com.geum.mvcServer.user.entity.UserRepository;
import com.geum.mvcServer.user.model.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User idCheck(String providerId) {
        User data = userRepository.findByProviderId(providerId);

        if(data.isEmpty()) {
            return null;
        } else {
            return data;
        }
    }

    public User registerUser(UserVO userVO) {
        User user = new User();

        user.setProviderId(userVO.getProviderId());
        user.setNickname(userVO.getNickname());

        User saveUser = userRepository.save(user);

        if (saveUser.isEmpty()) {
            return null;
        } else {
            return saveUser;
        }
    }


}
