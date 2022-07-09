package ramos.InCalifornia.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.global.config.jwt.JwtTokenProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    @InjectMocks
    JwtTokenProvider jwtTokenProvider;

    @Mock
    UserDetailsService userDetailsService;

    @Test
    void 토큰_생성() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_USER)
                .build();

        //when
        String token = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().toString());

        //then
        System.out.println(token);
        assertThat(token).isNotNull();
    }
    
    @Test
    void 토큰에서_이메일_추출() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_USER)
                .build();

        String token = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().toString());
        
        //when
        String email = jwtTokenProvider.extractEmail(token);
        
        //then
        assertThat(email).isEqualTo(member.getEmail());
    }

    @Test
    void 권한_정보_불러오기() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_USER)
                .build();

        String token = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().toString());
        AuthMember authMember = new AuthMember(member);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(authMember);

        //when
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        //then
        AuthMember principal = (AuthMember) authentication.getPrincipal();
        Member memberInToken = principal.getMember();
        assertThat(memberInToken.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void 유효한_토큰_검증() {
        //given
        String token = jwtTokenProvider.createAccessToken("test@test.com", "ROLE_USER");

        //when
        boolean isValidToken = jwtTokenProvider.isValidToken(token);

        //then
        assertThat(isValidToken).isTrue();
    }
}
