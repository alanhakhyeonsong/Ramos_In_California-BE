package ramos.InCalifornia.domain.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.dto.EnrollRequest;
import ramos.InCalifornia.domain.board.entity.Board;
import ramos.InCalifornia.domain.board.repository.BoardRepository;
import ramos.InCalifornia.domain.board.service.BoardService;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.domain.member.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static ramos.InCalifornia.support.BoardTestHelper.givenEnrollBoard;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    BoardService boardService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    BoardRepository boardRepository;

    private Member member;
    private AuthMember authMember;

    @BeforeEach
    void init() {
        member = Member.builder()
                .id(1L)
                .email("test@test.com")
                .password("test")
                .nickname("test")
                .role(Role.ROLE_ADMIN)
                .build();
        authMember = new AuthMember(member);
    }

    @Test
    @DisplayName("게시글 등록 성공")
    void createSuccess() {
        //given
        EnrollRequest dto = givenEnrollBoard();
        Board board = dto.toEntity(member);

        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(member));
        given(boardRepository.save(any(Board.class))).willReturn(board);

        //when
        BoardResponse result = boardService.create(dto, authMember);

        //then
        assertThat(result.getTitle()).isEqualTo(dto.getTitle());
        assertThat(result.getContent()).isEqualTo(dto.getContents());
        assertThat(result.getWriter()).isEqualTo(member.getNickname());
    }

    @Test
    @DisplayName("게시글 단건 조회 성공")
    void findByIdSuccess() {
        //given
        EnrollRequest dto = givenEnrollBoard();
        Board board = dto.toEntity(member);

        given(boardRepository.findById(any(Long.class))).willReturn(Optional.of(board));

        //when
        BoardResponse result = boardService.findById(1L);

        //then
        assertThat(result.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void editSuccess() {
        //given
        EnrollRequest dto = new EnrollRequest("제목 수정 테스트", "본문 수정 테스트");
        Board board = dto.toEntity(member);

        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(member));
        given(boardRepository.findById(any(Long.class))).willReturn(Optional.of(board));
        given(boardRepository.save(any(Board.class))).willReturn(board);

        //when
        BoardResponse result = boardService.edit(1L, dto, authMember);

        //then
        assertThat(result.getTitle()).isEqualTo(dto.getTitle());
        assertThat(result.getContent()).isEqualTo(dto.getContents());
        assertThat(result.getWriter()).isEqualTo(board.getMember().getNickname());
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void deleteSuccess() {
        //given
        Board board = Board.builder()
                .id(1L)
                .title("본문 테스트")
                .contents("본문 테스트")
                .member(member)
                .build();


        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(member));
        given(boardRepository.findById(any(Long.class))).willReturn(Optional.of(board));

        //when
        boardService.delete(1L, authMember);

        //then
        then(boardRepository)
                .should(times(1))
                .deleteById(any());
    }
}
