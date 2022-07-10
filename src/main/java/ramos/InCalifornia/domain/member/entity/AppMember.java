package ramos.InCalifornia.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ramos.InCalifornia.domain.member.exception.AnonymousHasNotIdException;

import static ramos.InCalifornia.domain.member.entity.Role.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppMember {

    private Long id;
    private String email;
    private Role role;

    public static AppMember anonymous() {
        return AppMember.builder()
                .role(ROLE_ANONYMOUS)
                .build();
    }

    public static AppMember user(Long memberId) {
        return AppMember.builder()
                .id(memberId)
                .role(ROLE_USER)
                .build();
    }

    public boolean isAnonymous() {
        return role.isAnonymous();
    }

    public long getId() {
        if (isAnonymous()) {
            throw new AnonymousHasNotIdException();
        }
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }
}
