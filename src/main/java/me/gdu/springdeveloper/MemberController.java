package me.gdu.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {

    //DI를 이용한 자동주입
    @Autowired
    private MemberService memberService;

    //요청을 받아서 적절한 비즈니스 로직으로 연결
    //http://localhost:8080/member 요청과 메서드를 연결
    @GetMapping("/member")
    public List<Member> getAllMembers(){
        return memberService.getAllMembers();
    }
}
