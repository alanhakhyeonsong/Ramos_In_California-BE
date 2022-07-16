package ramos.InCalifornia.domain.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
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
public class BoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    void createBoard() {
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

        //when
        Member savedMember = memberRepository.save(member);
        Board savedBoard = boardRepository.save(board);

        //then
        assertThat(savedBoard.getId()).isNotNull();
        assertThat(savedBoard.getTitle()).isEqualTo(board.getTitle());
        assertThat(savedBoard.getContents()).isEqualTo(board.getContents());
        assertThat(savedBoard.getMember()).isEqualTo(savedMember);
    }
}
