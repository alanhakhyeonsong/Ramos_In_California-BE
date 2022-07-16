package ramos.InCalifornia.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ramos.InCalifornia.domain.board.dto.BoardResponse;
import ramos.InCalifornia.domain.board.dto.QBoardResponse;
import ramos.InCalifornia.domain.board.entity.QBoard;
import ramos.InCalifornia.domain.member.entity.QMember;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardQuerydslRepositoryImpl implements BoardQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardResponse> findAllBoardWithPaging(Pageable pageable) {
        QBoard board = QBoard.board;
        QMember member = QMember.member;

        List<BoardResponse> results = queryFactory
                .select(new QBoardResponse(
                        board.id,
                        board.title,
                        board.contents,
                        board.updatedAt,
                        member.id,
                        member.nickname
                ))
                .from(board)
                .leftJoin(board.member, member)
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.updatedAt.desc())
                .fetch();

        Long totalCount = queryFactory
                .select(board.count())
                .from(board)
                .fetchOne();

        return new PageImpl<>(results, pageable, totalCount);
    }
}
