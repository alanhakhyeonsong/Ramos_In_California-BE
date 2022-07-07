package ramos.InCalifornia.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(500, "C001", "internal server error"),
    INVALID_TYPE_VALUE(400, "C002", "invalid type value"),
    ANONYMOUS_HAS_NOT_ID(500, "C003", "비회원의 ID는 조회 불가"),


    // Member
    MEMBER_NOT_FOUND(404, "M001", "등록되지 않은 사용자입니다."),
    NICKNAME_ALREADY_EXISTS(400, "M002", "닉네임 중복"),
    PASSWORD_DUPLICATED(400, "M003", "이전과 같은 비밀번호입니다.");

    private int status;
    private String code;
    private String message;
}
