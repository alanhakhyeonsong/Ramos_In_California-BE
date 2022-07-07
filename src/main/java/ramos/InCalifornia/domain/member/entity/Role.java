package ramos.InCalifornia.domain.member.entity;

public enum Role {
    ROLE_ANONYMOUS, ROLE_USER, ROLE_ADMIN;

    public boolean isAnonymous() {
        return this == ROLE_ANONYMOUS;
    }

    public boolean isUser() {
        return this == ROLE_USER;
    }

    public boolean isAdmin() {
        return this == ROLE_ADMIN;
    }
}
