package ramos.InCalifornia.config;

import org.junit.jupiter.api.Test;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.global.config.jwt.JwtTokenProvider;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
public class JwtTokenProviderTest {

    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("ThisIsSecretKeyTestValueAndLengthMustMoreThen512BitsWithOutBlanksHS512AlgorithmsRulesLetsTest");

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
}
