package ramos.InCalifornia.domain.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ramos.InCalifornia.domain.board.controller.BoardController;
import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.dto.EnrollRequest;
import ramos.InCalifornia.domain.board.service.BoardService;
import ramos.InCalifornia.domain.member.entity.AuthMember;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.support.WithMockAuthUser;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ramos.InCalifornia.support.BoardTestHelper.givenBoard;
import static ramos.InCalifornia.support.BoardTestHelper.givenUpdateBoard;


@WebMvcTest(controllers = BoardController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    BoardService boardService;

    @Test
    @DisplayName("게시글 등록 성공")
    @WithMockAuthUser(id = 1L, email = "test@test.com", role = Role.ROLE_ADMIN)
    void postSuccess() throws Exception {
        //given
        Map<String, String> input = new HashMap<>();
        input.put("title", "게시글 제목 테스트");
        input.put("contents", "게시글 본문 테스트");

        BoardResponse board = givenBoard();

        given(boardService.create(any(EnrollRequest.class), any(AuthMember.class))).willReturn(board);

        //andExpect
        mockMvc.perform(
                post("/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    // 단일 조회
    @Test
    @DisplayName("boardId로 단일 조회 성공")
    void findByBoardIdSuccess() throws Exception {
        //given
        BoardResponse board = givenBoard();

        given(boardService.findById(any(Long.class))).willReturn(board);

        //andExpect
        mockMvc.perform(
                get("/boards/{id}", 1L))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    // 수정
    @Test
    @DisplayName("게시글 수정 성공")
    @WithMockAuthUser(id = 1L, email = "test@test.com", role = Role.ROLE_ADMIN)
    void editBoardSuccess() throws Exception {
        //given
        Map<String, String> input = new HashMap<>();
        input.put("title", "게시글 제목 수정 테스트");
        input.put("contents", "게시글 본문 수정 테스트");

        BoardResponse result = givenUpdateBoard();

        given(boardService.edit(any(Long.class), any(EnrollRequest.class), any(AuthMember.class))).willReturn(result);

        //andExpect
        mockMvc.perform(
                put("/boards/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    // 삭제
    @Test
    @DisplayName("게시글 삭제 성공")
    void deleteBoardSuccess() throws Exception {
        //given
        doNothing().when(boardService).delete(any(Long.class), any(AuthMember.class));

        //andExpect
        mockMvc.perform(
                delete("/boards/{id}", 1L))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    // 페이징 목록 조회
}
