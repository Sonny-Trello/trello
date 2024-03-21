package io.superson.trelloproject.domain.status.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.status.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.superson.trelloproject.domain.status.entity.QStatus.status;

@Repository
@RequiredArgsConstructor
public class StatusQueryRepositoryImpl implements StatusQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findAllByBoardId(Long boardId) {
        return jpaQueryFactory
                .select(status.name)
                .from(status)
                .where(status.board.boardId.eq(boardId))
                .fetch();
    }

    @Override
    public Long getStatusCount(Long boardId) {
        return jpaQueryFactory.select(status.count())
                .from(status)
                .where(status.board.boardId.eq(boardId))
                .fetchFirst();
    }


    @Override
    public Status findPreviousStatus(Long boardId, float previousPositionNumber) {
        return jpaQueryFactory.select(status)
                .from(status)
                .where(status.board.boardId.eq(boardId))
                .where(status.statusNumber.lt(previousPositionNumber))
                .orderBy(status.statusNumber.desc())
                .fetchOne();
    }

    @Override
    public float getNextStatusNumberByStatusId(Long boardId, float previousPositionNumber) {
        return jpaQueryFactory.select(status.statusNumber)
                .from(status)
                .where(status.board.boardId.eq(boardId))
                .where(status.statusNumber.lt(previousPositionNumber))
                .orderBy(status.statusNumber.desc())
                .fetchFirst();
    }

}
