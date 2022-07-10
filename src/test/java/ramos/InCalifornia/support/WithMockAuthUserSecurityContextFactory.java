package ramos.InCalifornia.support;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;

public class WithMockAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAuthUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockAuthUser annotation) {
        Long id = annotation.id();
        String email = annotation.email();
        Role role = annotation.role();

        Member member = Member.builder()
                .id(id)
                .email(email)
                .password("test")
                .role(role)
                .build();

        AuthMember authMember = new AuthMember(member);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authMember, "password");
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
