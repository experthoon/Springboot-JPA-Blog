package com.cos.blog.controller;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.function.Supplier;

// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

    @Autowired //의존성 주입 DI
    private UserRepository userRepository;

    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //한페이지당 2건에 데이터를 리턴받아 볼 예정
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }

    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        // user/5를 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐 ?
        // 그럼 return null이 리턴이 되잖아.. 그럼 프로그램에 문제가 있지 않겠니 ?
        // Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단하게 return해
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. : " + id);
            }
        });

        //요청 : 웹브라우저
        //user 객체는 자바 오브젝트
        //변환 (웹브라우저가 이해할 수 있는 데이터) -> json
        //스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
        //만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        //user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
        return user;
    }

    @PostMapping("/dummy/join")
    public String join(User user){
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());

        user.setRole(RoleType.USER);
        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }

    //save 함수는 id를 전달하지 않으면 insert를 해주고
    //save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    //save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 합니다.

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){

        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }
        return "삭제되었습니다. id : " + id;

    }

    @Transactional // SAVE를 하지 않아도 업데이트가 가능 , 함수 종료시 자동 commit
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        System.out.println(id);
        System.out.println(requestUser.getPassword());
        System.out.println(requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{ // 이 때 영속화 변경을 감지 수정을 한다 이런걸 더티 체킹이라고 한다.
            return new IllegalArgumentException("수정에 실패");
        });

        user.setPassword(requestUser.getPassword()); // 값을 변경
        user.setEmail(requestUser.getEmail()); // 값을 변경

        // 종료가 되면서 transactional 로 인해 자동 커밋
        //userRepository.save(requestUser);

        //더티 체킹
        return user;
    }
}
