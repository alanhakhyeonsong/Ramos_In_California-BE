package ramos.InCalifornia.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ramos.InCalifornia.domain.board.dto.BoardDetailResponse;
import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.dto.EnrollRequest;
import ramos.InCalifornia.domain.board.service.BoardService;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.global.result.ResultCode;
import ramos.InCalifornia.global.result.ResultResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/boards")
    public ResponseEntity<ResultResponse> enroll(@RequestPart(value = "dto") EnrollRequest dto,
                                                 @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                 @AuthenticationPrincipal AuthMember authMember) {
        BoardResponse responseDto = boardService.create(dto, files, authMember);
        ResultResponse result = ResultResponse.of(ResultCode.ENROLL_SUCCESS, responseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<ResultResponse> findById(@PathVariable Long id) {
        BoardDetailResponse responseDto = boardService.findById(id);
        ResultResponse result = ResultResponse.of(ResultCode.FIND_BOARD_SUCCESS, responseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<ResultResponse> edit(@PathVariable Long id,
                     @RequestBody EnrollRequest dto,
                     @AuthenticationPrincipal AuthMember authMember) {
        BoardResponse responseDto = boardService.edit(id, dto, authMember);
        ResultResponse result = ResultResponse.of(ResultCode.UPDATE_BOARD_SUCCESS, responseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<ResultResponse> delete(@PathVariable Long id,
                       @AuthenticationPrincipal AuthMember authMember) {
        boardService.delete(id, authMember);
        ResultResponse result = ResultResponse.of(ResultCode.DELETE_BOARD_SUCCESS);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/boards")
    public ResponseEntity<ResultResponse> findAll(Pageable pageable) {
        Page<BoardResponse> response = boardService.findAll(pageable);
        ResultResponse result = ResultResponse.of(ResultCode.FIND_BOARD_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
