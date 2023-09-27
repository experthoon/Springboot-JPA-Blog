package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;


    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
        System.out.println("UserApiController : save 호출됨");
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK, 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson)
    }

    /*@PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession httpSession){
        System.out.println("login 호출");
        User principal = userService.로그인(user); //principal(접근주체)

        if (principal != null){
            httpSession.setAttribute("principal", principal);
        }
        return new ResponseDto<Integer>(HttpStatus.OK,1);
    }*/
}
