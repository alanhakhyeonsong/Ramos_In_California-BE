package ramos.InCalifornia.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.repository.MemberRepository;
import ramos.InCalifornia.support.TestJpaAuditingConfig;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestJpaAuditingConfig.class)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원등록 테스트")
    void createMember() {
        //given
        Member member = Member.builder()
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(savedMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(savedMember.getRole()).isEqualTo(member.getRole());
        assertThat(savedMember.getId()).isNotNull();
    }

    @Test
    @DisplayName("이메일로 회원 조회")
    void findByEmail() {
        //given
        Member member = Member.builder()
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        memberRepository.save(member);

        //when
        Member result = memberRepository.findByEmail(member.getEmail()).orElseThrow();

        //then
        assertThat(result.getEmail()).isEqualTo(member.getEmail());

    }

    @Test
    @DisplayName("이메일로 존재 여부 확인")
    void existsByEmail() {
        //given
        Member member = Member.builder()
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        //when
        memberRepository.save(member);
        boolean result = memberRepository.existsByEmail(member.getEmail());

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("닉네임으로 존재 여부 확인")
    void existsByNickname() {
        //given
        Member member = Member.builder()
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        //when
        memberRepository.save(member);
        boolean result = memberRepository.existsByNickname(member.getNickname());

        //then
        assertThat(result).isTrue();
    }
}
