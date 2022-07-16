package ramos.InCalifornia.domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.entity.Board;
import ramos.InCalifornia.domain.board.repository.BoardRepository;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.domain.member.repository.MemberRepository;
import ramos.InCalifornia.support.QuerydslTestConfig;
import ramos.InCalifornia.support.TestJpaAuditingConfig;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TestJpaAuditingConfig.class, QuerydslTestConfig.class})
public class BoardQuerydslRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("board 조회, 페이징 처리")
    void findAllBoardWithPaging() {
        //given
        Member member = Member.builder()
                .email("songs4805@naver.com")
                .nickname("Ramos")
                .password("test")
                .role(Role.ROLE_ADMIN)
                .build();

        Board board = Board.builder()
                .title("title test")
                .contents("contents test")
                .member(member)
                .build();

        Board board2 = Board.builder()
                .title("title test2")
                .contents("contents test2")
                .member(member)
                .build();

        Board board3 = Board.builder()
                .title("title test3")
                .contents("contents test3")
                .member(member)
                .build();

        //when
        memberRepository.save(member);
        boardRepository.save(board);
        boardRepository.save(board2);
        boardRepository.save(board3);

        //then
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BoardResponse> results = boardRepository.findAllBoardWithPaging(pageRequest);

        assertThat(results.getContent().size()).isEqualTo(3);
        assertThat(results.getTotalPages()).isEqualTo(1);
        assertThat(results.getContent().get(0).getTitle()).isEqualTo(board3.getTitle());
    }
}
