package ramos.InCalifornia.domain.board.entity;

import lombok.*;
import ramos.InCalifornia.domain.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "board_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Column(name = "path", nullable = false)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Image(String path, Board board) {
        this.path = path;
        this.board = board;
    }
}
