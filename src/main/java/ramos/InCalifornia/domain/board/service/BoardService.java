package ramos.InCalifornia.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.dto.EnrollRequest;
import ramos.InCalifornia.domain.board.entity.Board;
import ramos.InCalifornia.domain.board.exception.BoardNotFoundException;
import ramos.InCalifornia.domain.board.exception.NotWriterException;
import ramos.InCalifornia.domain.board.repository.BoardRepository;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.exception.MemberNotFoundException;
import ramos.InCalifornia.domain.member.repository.MemberRepository;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BoardResponse create(EnrollRequest dto, AuthMember authMember) {
        Member member = findMember(authMember);
        Board board = dto.toEntity(member);
        Board save = boardRepository.save(board);
        return BoardResponse.of(save);
    }

    private Member findMember(AuthMember authMember) {
        return memberRepository.findById(authMember.getMember().getId()).orElseThrow(MemberNotFoundException::new);
    }

    public BoardResponse findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        return BoardResponse.of(board);
    }

    @Transactional
    public BoardResponse edit(Long id, EnrollRequest dto, AuthMember authMember) {
        Member writer = findMember(authMember);
        Board board = findWriter(id, writer);
        board.editBoard(dto.getTitle(), dto.getContents());
        Board save = boardRepository.save(board);

        return BoardResponse.of(save);
    }

    private Board findWriter(Long id, Member writer) {
        Board findBoard = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        if (!Objects.equals(findBoard.getMember().getNickname(), writer.getNickname())) {
            throw new NotWriterException();
        }
        return findBoard;
    }

    @Transactional
    public void delete(Long id, AuthMember authMember) {
        Member writer = findMember(authMember);
        Board board = findWriter(id, writer);
        boardRepository.deleteById(id);
    }

    public Page<BoardResponse> findAll(Pageable pageable) {
        return boardRepository.findAllBoardWithPaging(pageable);
    }
}
