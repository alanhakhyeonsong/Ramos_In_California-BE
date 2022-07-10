package ramos.InCalifornia.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ramos.InCalifornia.domain.BaseEntity;
import ramos.InCalifornia.domain.member.exception.NicknameDuplicateException;
import ramos.InCalifornia.domain.member.exception.PasswordDuplicateException;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Column(name = "member_email", nullable = false, unique = true)
    private String email;
    @Column(name = "member_password", nullable = false)
    private String password;
    @Column(name = "member_nickname", nullable = false, unique = true)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public Member(Long id, String email, String password, String nickname, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public boolean isSameEmail(Member another) {
        return Objects.equals(this.email, another.getEmail());
    }

    public boolean isSameNickname(Member another) {
        return Objects.equals(this.nickname, another.getNickname());
    }

    public boolean isSamePassword(Member another) {
        return Objects.equals(this.password, another.getPassword());
    }

    public void updateNickname(Member another) {
        if (isSameNickname(another)) {
            throw new NicknameDuplicateException();
        }
        this.nickname = another.getNickname();
    }

    public void updatePassword(Member another) {
        if (isSamePassword(another)) {
            throw new PasswordDuplicateException();
        }
        setEncryptedPassword(another.getPassword());
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}
