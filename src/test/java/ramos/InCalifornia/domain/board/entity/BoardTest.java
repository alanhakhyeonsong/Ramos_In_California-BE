package ramos.InCalifornia.domain.board.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class BoardTest {

    @Test
    @DisplayName("Board 객체 생성 - 성공")
    void createBoard() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        //when, then
        assertThatCode(() -> Board.builder()
                .id(1L)
                .title("title test")
                .contents("contents test")
                .member(member)
                .build()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Board 객체 비교 - 성공")
    void compareBoard() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        Board board = Board.builder()
                .id(1L)
                .title("title test")
                .contents("contents test")
                .member(member)
                .build();

        Board anotherBoard = Board.builder()
                .id(2L)
                .title("title test2")
                .contents("contents test2")
                .member(member)
                .build();

        //when, then
        assertThat(board.getContents()).isNotEqualTo(anotherBoard.getContents());
        assertThat(board.getMember()).isEqualTo(anotherBoard.getMember());
    }

    @Test
    @DisplayName("Board 수정 - 성공")
    void editBoard() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        Board board = Board.builder()
                .id(1L)
                .title("title test")
                .contents("contents test")
                .member(member)
                .build();

        Board anotherBoard = Board.builder()
                .id(1L)
                .title("title 수정")
                .contents("contents 수정")
                .member(member)
                .build();

        //when
        board.editBoard(anotherBoard.getTitle(), anotherBoard.getContents());

        //then
        assertThat(board.getTitle()).isEqualTo(anotherBoard.getTitle());
        assertThat(board.getContents()).isEqualTo(anotherBoard.getContents());
        assertThat(board.getMember()).isEqualTo(anotherBoard.getMember());
    }
}
