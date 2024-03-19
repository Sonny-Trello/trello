package io.superson.trelloproject.domain.board.repository.query;

import static io.superson.trelloproject.domain.board.entity.QBoard.board;
import static io.superson.trelloproject.domain.board.entity.QUserBoard.userBoard;
import static io.superson.trelloproject.domain.user.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.board.entity.Board;
import java.util.List;

import io.superson.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findAllById(String userId) {
        return jpaQueryFactory
            .select(board)
            .from(userBoard, board)
            .where(userBoard.user.userId.eq(userId))
            .orderBy(board.modifiedAt.desc())
            .fetch();
    }

    @Override
    public List<User> findAllByBoardId(Long boardId) {
        return jpaQueryFactory.select(user)
                .from(user)
                .join(userBoard).on(user.userId.eq(userBoard.user.userId))
                .where(userBoard.board.boardId.eq(boardId))
                .fetch();
    }
}
