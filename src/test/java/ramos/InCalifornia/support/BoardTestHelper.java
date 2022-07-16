package ramos.InCalifornia.support;

import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.dto.EnrollRequest;

import java.time.LocalDateTime;

public class BoardTestHelper {

    public static BoardResponse givenBoard() {
        return new BoardResponse(1L, "게시글 제목 테스트", "게시글 본문 테스트", LocalDateTime.now(), 1L, "test");
    }

    public static BoardResponse givenUpdateBoard() {
        return new BoardResponse(1L, "게시글 제목 수정 테스트", "게시글 본문 수정 테스트", LocalDateTime.now(), 1L, "test");
    }

    public static EnrollRequest givenEnrollBoard() {
        return new EnrollRequest("게시글 제목 생성 테스트", "게시글 본문 생성 테스트");
    }
}
