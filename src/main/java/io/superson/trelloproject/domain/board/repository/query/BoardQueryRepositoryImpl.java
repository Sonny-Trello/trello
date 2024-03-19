package io.superson.trelloproject.domain.board.repository.query;

import static io.superson.trelloproject.domain.board.entity.QBoard.board;
import static io.superson.trelloproject.domain.board.entity.QUserBoard.userBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.board.entity.Board;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findAllById(String userId) {
        return jpaQueryFactory.select(board)
            .from(board, userBoard)
            .where(userBoard.user.userId.eq(userId))
            .orderBy(board.modifiedAt.desc())
            .fetch();
    }
}
