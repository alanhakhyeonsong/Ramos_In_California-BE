package ramos.InCalifornia.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.dto.EnrollRequest;
import ramos.InCalifornia.domain.board.service.BoardService;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.global.result.ResultCode;
import ramos.InCalifornia.global.result.ResultResponse;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/board")
    public ResponseEntity<ResultResponse> enroll(@RequestBody EnrollRequest dto,
                                                 @AuthenticationPrincipal AuthMember authMember) {
        BoardResponse responseDto = boardService.create(dto, authMember);
        ResultResponse result = ResultResponse.of(ResultCode.ENROLL_SUCCESS, responseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<ResultResponse> findById(@PathVariable Long id) {
        BoardResponse responseDto = boardService.findById(id);
        ResultResponse result = ResultResponse.of(ResultCode.FIND_BOARD_SUCCESS, responseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<ResultResponse> edit(@PathVariable Long id,
                     @RequestBody EnrollRequest dto,
                     @AuthenticationPrincipal AuthMember authMember) {
        BoardResponse responseDto = boardService.edit(id, dto, authMember);
        ResultResponse result = ResultResponse.of(ResultCode.UPDATE_BOARD_SUCCESS, responseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<ResultResponse> delete(@PathVariable Long id,
                       @AuthenticationPrincipal AuthMember authMember) {
        boardService.delete(id, authMember);
        ResultResponse result = ResultResponse.of(ResultCode.DELETE_BOARD_SUCCESS);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
