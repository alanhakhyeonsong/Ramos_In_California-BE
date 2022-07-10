package ramos.InCalifornia.support;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import ramos.InCalifornia.domain.member.entity.AppMember;
import ramos.InCalifornia.domain.member.entity.Role;

public class WithMockAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAuthUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockAuthUser annotation) {
        Long id = annotation.id();
        String email = annotation.email();
        Role role = annotation.role();

        AppMember appMember = AppMember.builder()
                .id(id)
                .email(email)
                .role(role)
                .build();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(appMember, "password");
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
