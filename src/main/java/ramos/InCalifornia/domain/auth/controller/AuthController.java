package ramos.InCalifornia.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ramos.InCalifornia.domain.auth.dto.*;
import ramos.InCalifornia.domain.auth.service.AuthService;
import ramos.InCalifornia.global.config.jwt.TokenDto;
import ramos.InCalifornia.global.result.ResultCode;
import ramos.InCalifornia.global.result.ResultResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signUp(@Valid @RequestBody SignUpRequest dto) {
        MemberResponse responseDto = authService.signUp(dto);
        ResultResponse result = ResultResponse.of(ResultCode.REGISTER_SUCCESS, responseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@Valid @RequestBody LoginRequest dto) {
        TokenDto tokenDto = authService.login(dto);
        ResultResponse result = ResultResponse.of(ResultCode.LOGIN_SUCCESS, tokenDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ResultResponse> reIssue(@Valid @RequestBody ReissueRequest dto) {
        TokenDto tokenDto = authService.reissue(dto);
        ResultResponse result = ResultResponse.of(ResultCode.REISSUE_SUCCESS, tokenDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResultResponse> logout(@Valid @RequestBody LogoutRequest dto) {
        authService.logout(dto.getRefreshToken());
        ResultResponse result = ResultResponse.of(ResultCode.LOGOUT_SUCCESS, null);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
