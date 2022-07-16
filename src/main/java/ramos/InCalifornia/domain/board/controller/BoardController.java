package ramos.InCalifornia.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/board")
    public ResponseEntity<ResultResponse> enroll(@RequestBody EnrollRequest dto,
                                                 @AuthenticationPrincipal AuthMember authMember) {
        BoardResponse responseDto = boardService.create(dto, authMember);
        ResultResponse result = ResultResponse.of(ResultCode.ENROLL_SUCCESS, responseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @GetMapping("/board/{id}")
    public void findById(@PathVariable Long id) {

    }

    @PutMapping("/board/{id}")
    public void edit(@PathVariable Long id) {

    }

    @DeleteMapping("/board/{id}")
    public void delete(@PathVariable Long id) {

    }
}
