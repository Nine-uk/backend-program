package me.gdu.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(member.getName()).isEqualTo("고길동");
    }
}