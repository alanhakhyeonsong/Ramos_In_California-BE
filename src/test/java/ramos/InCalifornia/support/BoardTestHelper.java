package ramos.InCalifornia.support;

import ramos.InCalifornia.domain.board.dto.BoardResponse;

import java.time.LocalDateTime;

public class BoardTestHelper {

    public static BoardResponse givenBoard() {
        return new BoardResponse(1L, "게시글 제목 테스트", "게시글 본문 테스트", LocalDateTime.now(), 1L, "test");
    }
}
