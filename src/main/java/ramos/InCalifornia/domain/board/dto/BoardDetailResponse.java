package ramos.InCalifornia.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ramos.InCalifornia.domain.board.entity.Board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardDetailResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private Long memberId;
    private String writer;

    private List<String> paths;

    public static BoardDetailResponse of(Board board) {
        List<String> paths = board.getImages().stream()
                .map(i -> i.getPath())
                .collect(Collectors.toList());

        return new BoardDetailResponse(board.getId(), board.getTitle(), board.getContents(), board.getUpdatedAt(),
                board.getMember().getId(), board.getMember().getNickname(), paths);
    }

    public BoardDetailResponse(Long boardId, String title, String content, LocalDateTime updatedAt, Long memberId, String writer, List<String> paths) {
        this.id = boardId;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
        this.memberId = memberId;
        this.writer = writer;
        this.paths = paths;
    }

}
