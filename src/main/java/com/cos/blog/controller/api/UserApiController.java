package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
        System.out.println("UserApiController : save 호출됨");
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK, 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson)
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user){ //User 객체를 Json 데이터로 받기 때문에
        userService.회원수정(user);
        // 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
        // 하지만 세션값은 변경되지 않은 상태이기 때문에 직접 세션값을 변경

        // 세션 등록. 업데이트된 세션은 사용자의 새로운 인증 정보를 반영하고, 보안을 유지하기 위해 사용.
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())); // 토큰 생성
        SecurityContextHolder.getContext().setAuthentication(authentication); // 현재 스레드의 보안 컨텍스트에 사용자 인증 정보를 설정. 이를 통해 사용자 세션에 대한 업데이트

        return new ResponseDto<Integer>(HttpStatus.OK, 1); // json 응답
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
