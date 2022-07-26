package ramos.InCalifornia.domain.board.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ramos.InCalifornia.domain.BaseEntity;
import ramos.InCalifornia.domain.member.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Column(name = "board_title", nullable = false)
    private String title;

    @Column(name = "board_contents", nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @Builder
    public Board(Long id, String title, String contents, Member member) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.member = member;
        this.images = new ArrayList<>();
    }

    public void editBoard(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setImages(List<Image> images) {
        this.images = images;
        if (images == null) {
            return;
        }
        for (Image image : images) {
            if (image.getBoard() == null) {
                image.setBoard(this);
            }
        }
    }
}