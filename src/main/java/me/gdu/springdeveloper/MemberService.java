package me.gdu.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    //이친구도 뭐하는 앤지 찾기
    //스프링 빈으로 등록되는 객체만 주입받을수 있고 스프링 빈 객체는 엔티티 리파지토리 서비스 컨트롤러
    @Autowired
    private MemberRepository memberRepository;

    // 멤버 테이블에 있는 모든 레코드들을 읽어서 반환하는 비지니스 로직
    public List<Member> getAllMembers() {
        //널 익셉션이 뜨지않는 이유 적기
        //memberRepository에 findAll 매세드가 없어도 실행가능한 이유는 JpaRepository 덕분
        return memberRepository.findAll(); // select * from member과 동일 기능
    }
}
