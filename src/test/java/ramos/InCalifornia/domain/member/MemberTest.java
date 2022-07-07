package ramos.InCalifornia.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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
    void updateNicknameSuccess() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        //when
        String newNickname = "Sergio Ramos";

        //then
        assertThat(member.getNickname()).isEqualTo(newNickname);
    }

    @Test
    @DisplayName("nickname이 중복일 경우 변경 불가")
    void updateNicknameFail() {
        //given

        //when

        //then
    }

    @Test
    void updatePasswordSuccess() {
        //given

        //when

        //then
    }

    @Test
    void updatePasswordFail() {
        //given

        //when

        //then
    }
}
