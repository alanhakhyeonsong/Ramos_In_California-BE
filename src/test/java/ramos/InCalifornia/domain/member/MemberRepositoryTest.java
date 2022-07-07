package ramos.InCalifornia.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원등록_테스트() throws Exception {
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
}