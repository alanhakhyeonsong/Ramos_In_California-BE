package ramos.InCalifornia.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ramos.InCalifornia.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardQuerydslRepository {
}
