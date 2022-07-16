package ramos.InCalifornia.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ramos.InCalifornia.domain.board.dto.BoardResponse;

public interface BoardQuerydslRepository {

    Page<BoardResponse> findAllBoardWithPaging(Pageable pageable);
}
