package ramos.InCalifornia.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // Auth
    REGISTER_SUCCESS(200, "A001", "회원가입 되었습니다."),
    LOGIN_SUCCESS(200, "A002", "로그인 되었습니다."),
    REISSUE_SUCCESS(200, "A003", "토큰 재발급 성공"),
    LOGOUT_SUCCESS(200, "A004", "로그아웃 되었습니다.");

    private final int status;
    private final String code;
    private final String message;
}
