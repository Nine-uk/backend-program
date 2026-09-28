package me.gdu.springdeveloper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    // 의존성 주입
    // 스프링 빈으로 등록되는 객체만 주입받을수 있고 스프링 빈 객체는 엔티티 리파지토리 서비스 컨트롤러
    @Autowired
    private MemberRepository memberRepository;

    // 멤버 테이블에 있는 모든 레코드들을 읽어서 반환하는 비지니스 로직
    public List<Member> getAllMembers() {
        // 널 익셉션이 뜨지않는 이유 적기
        // memberRepository에 findAll 매세드가 없어도 실행가능한 이유는 JpaRepository 덕분
        return memberRepository.findAll(); // select * from member과 동일 기능
    }

    // 멤버 저장
    public Member saveMember(@RequestBody Member member) {
        // insert into members values(...) 를 save가 실행 함 ( insert or update 기능 실행)
        // 단 넘어온 객체에 아이디 값이 있으면 이미 있는 레코드라고 생각해 update를 실행함 그러면 없는 아이디를 업데이트하며 오류가 발생
        return memberRepository.save(member);
    }
}
