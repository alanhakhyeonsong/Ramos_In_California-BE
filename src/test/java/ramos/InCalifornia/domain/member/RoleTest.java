package ramos.InCalifornia.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ramos.InCalifornia.domain.member.entity.Role;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleTest {

    @Test
    @DisplayName("권한이 없는 회원")
    void roleAnonymousTest() throws Exception {
        //given
        Role anonymous = Role.ROLE_ANONYMOUS;

        //when, then
        assertThat(anonymous.isAnonymous()).isTrue();
    }

    @Test
    @DisplayName("일반 권한인 회원")
    void roleUserTest() {
        //given
        Role user = Role.ROLE_USER;

        //when, then
        assertThat(user.isUser()).isTrue();
    }

    @Test
    @DisplayName("관리자 권한인 회원")
    void roleAdminTest() {
        //given
        Role admin = Role.ROLE_ADMIN;

        //when, then
        assertThat(admin.isAdmin()).isTrue();
    }
}
