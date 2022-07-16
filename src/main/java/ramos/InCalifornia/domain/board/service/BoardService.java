package ramos.InCalifornia.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.dto.EnrollRequest;
import ramos.InCalifornia.domain.board.entity.Board;
import ramos.InCalifornia.domain.board.exception.BoardNotFoundException;
import ramos.InCalifornia.domain.board.repository.BoardRepository;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.exception.MemberNotFoundException;
import ramos.InCalifornia.domain.member.repository.MemberRepository;

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

    public BoardResponse edit(Long id, EnrollRequest dto, AuthMember authMember) {
        return null;
    }

    public void delete(Long id, AuthMember authMember) {

    }
}
