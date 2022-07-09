package ramos.InCalifornia.domain.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ramos.InCalifornia.domain.auth.dto.LoginRequest;
import ramos.InCalifornia.domain.auth.dto.MemberResponse;
import ramos.InCalifornia.domain.auth.dto.ReissueRequest;
import ramos.InCalifornia.domain.auth.dto.SignUpRequest;
import ramos.InCalifornia.domain.auth.exception.MemberAlreadyExistException;
import ramos.InCalifornia.domain.auth.service.AuthService;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.domain.member.repository.MemberRepository;
import ramos.InCalifornia.global.config.jwt.JwtTokenProvider;
import ramos.InCalifornia.global.config.jwt.TokenDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    JwtTokenProvider tokenProvider;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccess() {
        //given
        SignUpRequest dto = new SignUpRequest("test@test.com", "Ramos", "test");
        Member member = dto.toEntity();
        given(memberRepository.existsByEmail(dto.getEmail())).willReturn(false);
        given(memberRepository.save(any(Member.class))).willReturn(member);

        //when
        MemberResponse response = authService.signUp(dto);

        //then
        assertThat(response.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 존재하는 회원")
    void signUpFailed() {
        //given
        SignUpRequest dto = new SignUpRequest("test@test.com", "Ramos", "test");
        Member member = dto.toEntity();
        given(memberRepository.existsByEmail(dto.getEmail())).willReturn(true);

        //when, then
        assertThatThrownBy(() -> authService.signUp(dto))
                .isInstanceOf(MemberAlreadyExistException.class);
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@test.com")
                .password("test")
                .role(Role.ROLE_USER)
                .build();

        LoginRequest request = new LoginRequest(member.getEmail(), member.getPassword());

        given(memberRepository.findByEmail("test@test.com")).willReturn(Optional.of(member));
        given(passwordEncoder.matches("test", member.getPassword())).willReturn(true);

        //when
        TokenDto token = authService.login(request);

        //then
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void reissueSuccess() {
        //given
        ReissueRequest dto = new ReissueRequest("accessToken", "refreshToken");

        String email = "test@test.com";
        String role = Role.ROLE_USER.toString();
        String accessToken = "new";
        given(tokenProvider.extractEmail(any())).willReturn("test@test.com");
        given(tokenProvider.isValidToken(any())).willReturn(true);
        given(tokenProvider.createAccessToken(email, role)).willReturn(accessToken);

        //when
        TokenDto token = authService.reissue(dto);

        //then
        assertThat(token.getAccessToken()).isNotEqualTo(dto.getAccessToken());
    }
}
