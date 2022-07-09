package ramos.InCalifornia.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일의 형식이 맞지 않습니다.")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(getEmail())
                .password(getPassword())
                .nickname(getNickname())
                .role(Role.ROLE_USER)
                .build();
    }
}
