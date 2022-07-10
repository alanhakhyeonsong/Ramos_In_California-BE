package ramos.InCalifornia.domain.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ramos.InCalifornia.domain.auth.controller.AuthController;
import ramos.InCalifornia.domain.auth.dto.LoginRequest;
import ramos.InCalifornia.domain.auth.dto.ReissueRequest;
import ramos.InCalifornia.domain.auth.service.AuthService;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.global.config.jwt.TokenDto;
import ramos.InCalifornia.support.TokenTestHelper;
import ramos.InCalifornia.support.WithMockAuthUser;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private AuthMember authMember;

    @MockBean
    AuthService authService;

    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccess() throws Exception {
        //given
        Map<String, String> input = new HashMap<>();
        input.put("email", "test@test.com");
        input.put("nickname", "Ramos");
        input.put("password", "test");

        //andExpect
        mockMvc.perform(
                post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - 올바른 email 형식이 아닌 경우")
    void signUpFailed() throws Exception {
        //given
        Map<String, String> input = new HashMap<>();
        input.put("email", "test");
        input.put("nickname", "Ramos");
        input.put("password", "test");

        //andExpect
        mockMvc.perform(
                post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        //given
        Map<String, String> input = new HashMap<>();
        input.put("email", "test@test.com");
        input.put("password", "test");
        LoginRequest request = new LoginRequest("test@test.com", "test");
        TokenDto tokenDto = new TokenDto("accessToken", "refreshToken");
        given(authService.login(request)).willReturn(tokenDto);

        //andExpect
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 - 올바른 email 형식이 아닌 경우")
    void loginFailed() throws Exception {
        //given
        Map<String, String> input = new HashMap<>();
        input.put("email", "test");
        input.put("password", "test");

        //andExpect
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logoutSuccess() throws Exception {
        //given
        Map<String, String> input = new HashMap<>();
        input.put("refreshToken", "refreshTokenTest");

        //andExpect
        mockMvc.perform(post("/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }


    @Test
    @DisplayName("토큰 재발급 성공")
    @WithMockAuthUser(email = "test@test.com", id = 1L, role = Role.ROLE_USER)
    void tokenReissue() throws Exception {
        //given
        TokenDto token = getToken();
        Map<String, String> input = new HashMap<>();
        input.put("accessToken", token.getAccessToken());
        input.put("refreshToken", token.getRefreshToken());

        given(authService.reissue(any(String.class), any(ReissueRequest.class))).willReturn(token);

        //andExpect
        mockMvc.perform(post("/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    private TokenDto getToken() {
        return TokenTestHelper.givenToken();
    }
}
