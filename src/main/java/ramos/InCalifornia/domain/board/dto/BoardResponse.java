package ramos.InCalifornia.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ramos.InCalifornia.domain.board.entity.Board;
import ramos.InCalifornia.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private Long memberId;
    private String writer;

    public static BoardResponse of(Board board, Member member) {
        return new BoardResponse(board.getId(), board.getTitle(), board.getContents(), board.getUpdatedAt(), member.getId(), member.getNickname());
    }

    public static BoardResponse of(Board board) {
        return new BoardResponse(board.getId(), board.getTitle(), board.getContents(), board.getUpdatedAt(), board.getMember().getId(), board.getMember().getNickname());
    }

    @QueryProjection
    public BoardResponse(Long boardId, String title, String content, LocalDateTime updatedAt, Long memberId, String writer) {
        this.id = boardId;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
        this.memberId = memberId;
        this.writer = writer;
    }
}
