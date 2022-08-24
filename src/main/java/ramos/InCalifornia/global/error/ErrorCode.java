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
    BAD_CREDENTIALS(400, "C004", "bad credentials"),
    METHOD_NOT_ALLOWED(405, "C005", "method not allowed"),
    UNAUTHORIZED(401, "C006", "권한이 없습니다."),

    // Auth
    ID_PASSWORD_IS_WRONG(400, "A001", "id, password를 다시 확인해주세요."),
    MEMBER_ALREADY_EXISTS(400, "A002", "이미 존재하는 회원입니다."),
    TOKEN_EXPIRED(400, "A003", "토큰 유효기간 만료"),

    // Member
    MEMBER_NOT_FOUND(404, "M001", "등록되지 않은 사용자입니다."),
    NICKNAME_ALREADY_EXISTS(400, "M002", "닉네임 중복"),
    PASSWORD_DUPLICATED(400, "M003", "이전과 같은 비밀번호입니다."),

    // Board
    BOARD_NOT_FOUND(404, "B001", "게시글을 찾을 수 없습니다."),
    NOT_WRITER(401, "B002", "작성자가 아닙니다."),
    INVALID_FILE_TYPE(401, "B003", "jpg, jpeg 형식의 이미지 파일이 아닙니다."),
    FILE_IS_EMPTY(401, "B004", "파일을 첨부하지 않았습니다.");

    private int status;
    private String code;
    private String message;
}
