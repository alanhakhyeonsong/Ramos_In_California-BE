package ramos.InCalifornia.domain.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ramos.InCalifornia.domain.member.exception.AnonymousHasNotIdException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AppMemberTest {

    @Test
    @DisplayName("익명 사용자 생성 성공")
    void createAnonymous() {
        //given
        AppMember anonymous = AppMember.anonymous();

        //when
        boolean result = anonymous.isAnonymous();

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("익명 사용자는 조회 불가")
    void findUserFail() {
        //given
        AppMember anonymous = AppMember.anonymous();

        //when, then
        assertThatThrownBy(() -> anonymous.getId())
                .isInstanceOf(AnonymousHasNotIdException.class);
    }

    @Test
    @DisplayName("익명 사용자가 아닌 권한이 있는 경우 조회 성공")
    void findUserSuccess() {
        //given
        AppMember user = AppMember.user(1L);

        //when
        long id = user.getId();

        //then
        assertThat(id).isEqualTo(1L);
        assertThat(user.getRole()).isEqualTo(Role.ROLE_USER);
    }
}
