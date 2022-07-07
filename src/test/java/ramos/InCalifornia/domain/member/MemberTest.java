package ramos.InCalifornia.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @Test
    @DisplayName("Member 객체 생성 - 성공")
    void createMember() {
        assertThatCode(() -> Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Member 객체 비교 - 성공")
    void compareMember() throws Exception {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        Member anotherMember = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        Member notSame = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Sergio")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        //when, then
        assertThat(member.isSameEmail(anotherMember)).isTrue();
        assertThat(member.isSameNickname(anotherMember)).isTrue();
        assertThat(member.isSameNickname(notSame)).isFalse();
    }

    @Test
    @DisplayName("nickname 변경 성공")
    void updateNicknameSuccess() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        Member updateMember = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Sergio Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        //when
        member.updateNickname(updateMember);

        //then
        assertThat(member.getNickname()).isEqualTo(updateMember.getNickname());
    }

    @Test
    @DisplayName("nickname이 중복일 경우 변경 불가")
    void updateNicknameFail() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        Member updateMember = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        //when, then
        assertThatThrownBy(() -> member.updateNickname(updateMember))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("password 변경 성공")
    void updatePasswordSuccess() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        Member updateMember = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test2")
                .role(Role.ROLE_ADMIN)
                .build();

        //when
        member.updatePassword(updateMember);

        //then
        assertThat(member.getPassword()).isEqualTo(updateMember.getPassword());
    }

    @Test
    @DisplayName("이전 password와 같을 경우 변경 불가")
    void updatePasswordFail() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        Member updateMember = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        //when, then
        assertThatThrownBy(() -> member.updatePassword(updateMember))
                .isInstanceOf(RuntimeException.class);
    }
}
