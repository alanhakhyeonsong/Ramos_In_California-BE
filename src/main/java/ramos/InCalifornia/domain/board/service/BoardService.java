package ramos.InCalifornia.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ramos.InCalifornia.domain.board.dto.BoardDetailResponse;
import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.dto.EnrollRequest;
import ramos.InCalifornia.domain.board.entity.Board;
import ramos.InCalifornia.domain.board.entity.Image;
import ramos.InCalifornia.domain.board.exception.BoardNotFoundException;
import ramos.InCalifornia.domain.board.exception.FileInvalidException;
import ramos.InCalifornia.domain.board.exception.NotWriterException;
import ramos.InCalifornia.domain.board.repository.BoardRepository;
import ramos.InCalifornia.domain.file.service.StorageService;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.exception.MemberNotFoundException;
import ramos.InCalifornia.domain.member.repository.MemberRepository;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final StorageService storageService;

    @Transactional
    public BoardResponse create(EnrollRequest dto, List<MultipartFile> files, AuthMember authMember) {
        Member member = findMember(authMember);
        Board board = dto.toEntity(member);

        files.stream().map(MultipartFile::getContentType)
                .filter(Predicate.not(this::isImage))
                .findAny().ifPresent(e -> {
                    throw new FileInvalidException();
                });

        List<String> imagePaths = storageService.store(files);
        List<Image> images = imagePaths.stream().map(path -> Image.builder().path(path).build()).collect(Collectors.toList());
        board.setImages(images);

        Board save = boardRepository.save(board);
        return BoardResponse.of(save);
    }

    private boolean isImage(String fileType) {
        return fileType.equals(MediaType.IMAGE_JPEG_VALUE) || fileType.equals(MediaType.IMAGE_PNG_VALUE);
    }

    private Member findMember(AuthMember authMember) {
        return memberRepository.findById(authMember.getMember().getId()).orElseThrow(MemberNotFoundException::new);
    }

    public BoardDetailResponse findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        return BoardDetailResponse.of(board);
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
        boardRepository.deleteById(board.getId());
    }

    public Page<BoardResponse> findAll(Pageable pageable) {
        return boardRepository.findAllBoardWithPaging(pageable);
    }
}
