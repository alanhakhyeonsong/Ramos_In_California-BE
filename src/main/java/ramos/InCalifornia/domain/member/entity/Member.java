package ramos.InCalifornia.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email", nullable = false, unique = true)
    private String email;
    @Column(name = "member_password", nullable = false)
    private String password;
    @Column(name = "member_nickname", nullable = false, unique = true)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String password, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public AppMember toAppMember() {
        return AppMember.builder()
                .id(id)
                .role(role)
                .build();
    }

}
