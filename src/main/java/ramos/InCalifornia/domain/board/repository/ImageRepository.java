package ramos.InCalifornia.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ramos.InCalifornia.domain.board.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
