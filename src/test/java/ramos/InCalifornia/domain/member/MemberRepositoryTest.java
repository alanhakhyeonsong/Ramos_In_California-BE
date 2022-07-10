package ramos.InCalifornia.domain.member;

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

    @Test
    void 회원등록_테스트() {
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
        assertThat(savedMember.getId()).isEqualTo(1L);
    }

    @Test
    void 이메일로_조회() {
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
    void 이메일로_존재_여부_확인() {
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
    void 닉네임으로_존재_여부_확인() {
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
