package ramos.InCalifornia.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ramos.InCalifornia.domain.board.entity.Board;
import ramos.InCalifornia.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
