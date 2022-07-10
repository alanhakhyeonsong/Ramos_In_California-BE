package ramos.InCalifornia.support;

import org.springframework.security.test.context.support.WithSecurityContext;
import ramos.InCalifornia.domain.member.entity.Role;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockAuthUserSecurityContextFactory.class)
public @interface WithMockAuthUser {
    long id();
    String email();
    Role role();
}
