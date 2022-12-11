package com.geum.mvcServer.apis.user.login.controller;

import com.geum.mvcServer.apis.user.entity.User;
import com.geum.mvcServer.apis.user.login.service.LoginService;
import com.geum.mvcServer.apis.user.model.UserLoginDTO;
import com.geum.mvcServer.config.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
        userLoginDTO.setPassword(bCryptPasswordEncoder.encode(userLoginDTO.getPassword()));
        User user = loginService.loginUser(userLoginDTO.getUsername(), userLoginDTO.getPassword());

        if(user == null) {
            return "로그인 오류";
        } else if(user.getRole() == null) {
            return "탈퇴된 계정입니다";
        }

        sessionManager.createSession(user, response);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }
}
