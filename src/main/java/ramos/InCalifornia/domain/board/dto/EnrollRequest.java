package ramos.InCalifornia.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ramos.InCalifornia.domain.board.entity.Board;
import ramos.InCalifornia.domain.member.entity.Member;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    public Board toEntity(Member member) {
        return Board.builder()
                .title(getTitle())
                .contents(getContents())
                .member(member)
                .build();
    }
}
