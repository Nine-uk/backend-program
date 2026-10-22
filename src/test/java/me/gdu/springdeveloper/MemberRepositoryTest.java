package me.gdu.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// Repository Test를 위한 Bean 생성 및 각 테스트실행 후 Rollback 실행( @Transactional )
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @DisplayName("member 테이블의 모든 레코드 가져오기")
    @Test
    public void getAllMembers(){
        // 준비 given

        // 실행 when
        List<Member> members = memberRepository.findAll();

        // 검증then
        assertThat(members.size()).isEqualTo(3);
    }

    // 커밋 되지않아 각각의 insert가 영향을 안끼침
    @Sql("/insert-members.sql")
    @DisplayName("member 테이블에서 id가 2인 레코드 가져오기")
    @Test
    public void getMemberById2(){
        // 준비 given

        // 실행 when
        Member member = memberRepository.findById(2L).get();

        // 검증 then
        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insert-members.sql")
    @DisplayName("member 테이블에서 Name이 B인 레코드 가져오기")
    @Test
    public void getMemberByName(){
        // 준비 given

        // 실행 when
        // List<Member> member = memberRepository.findByName("B"); // select * from member where name='B';
        Member member = memberRepository.findByName("B").get();

        // 검증 then
//        assertThat(member.size()).isEqualTo(1);
//        assertThat(member.get(0).getId()).isEqualTo(2);
        assertThat(member.getId()).isEqualTo(2L);
    }

    @DisplayName("하나의 Member객체를 DB에 저장하기")
    @Test
    public void saveMember(){
        // 준비 given : 데이터 준비
        Member member = new Member("A");

        // 실행 when
        memberRepository.save(member);

        // 검증 then
        List<Member> members = memberRepository.findAll();
        assertThat(members.get(0).getId()).isEqualTo(1L);
    }

    @DisplayName("3개의 Member 객체를 DB에 저장하기")
    @Test
    public void saveMembers(){
        // 준비 given
//        List<Member> members = new ArrayList<Member>(); // ArrayList<Member> Member생략 가능 지네틱이
//        for (int i = 0; i < 3; i++){
//            members.add(new Member("A"+i));
//        }
        List<Member> members = List.of(new Member("A1"), new Member("A2"), new Member("A3"));

        // 실행 when
        memberRepository.saveAll(members);

        // 검증 then
        assertThat(memberRepository.findAll().size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @DisplayName("Id가 2인 레코드 삭제")
    @Test
    public void deleteMemberById2(){
        // 준비 given : @Sql로 대체

        // 실행 when
        memberRepository.deleteById(2L);

        // 검증 then
        // assertThat(memberRepository.findAll().size()).isEqualTo(2);
        Optional<Member> member = memberRepository.findById(2L);
        // assertThat(member.isEmpty()).isEqualTo(true) 와 같은 기능
        assertThat(member.isEmpty()).isTrue();
    }

    @Sql("/insert-members.sql")
    @DisplayName("모든 레코드 삭제하기")
    @Test
    public void deleteAllMembers(){
        // 준비 given : @Sql로 대체

        // 실행 when
        memberRepository.deleteAll();

        // 검증 then
        assertThat(memberRepository.findAll().isEmpty()).isTrue();
    }

    // 내부적으로 사용되는 영속성 컨텍스트의 변경감지(dirty-checking) 기능을 이용한 레코드 변경
    @Sql("/insert-members.sql")
    @DisplayName("변경감지 기능을 이용해 id가 2인 레코드의 name을 '홍길동'으로 변경하기")
    @Test
    public void updateMember(){
        // 준비 given
        Member member = memberRepository.findById(2L).get();

        // 실행 when
        member.setName("홍길동");

        // 검증 then
        member = memberRepository.findById(2L).get();
        assertThat(member.getName()).isEqualTo("홍길동");
    }
}