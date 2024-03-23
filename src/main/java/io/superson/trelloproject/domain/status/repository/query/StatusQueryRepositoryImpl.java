package io.superson.trelloproject.domain.status.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.status.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<Status> findPreviousStatus(Long boardId, Float previousPositionNumber) {
        return Optional.ofNullable(jpaQueryFactory.select(status)
                .from(status)
                .where(status.board.boardId.eq(boardId))
                .where(status.statusNumber.lt(previousPositionNumber))
                .orderBy(status.statusNumber.desc())
                .fetchFirst());
    }

    @Override
    public Optional<Status> findFollowingStatus(Long boardId, Float previousPositionNumber) {
        return Optional.ofNullable(jpaQueryFactory.select(status)
                .from(status)
                .where(status.board.boardId.eq(boardId))
                .where(status.statusNumber.gt(previousPositionNumber))
                .orderBy(status.statusNumber.desc())
                .fetchFirst());
    }

    @Override
    public float getPreviousStatusNumberByStatusId(Long boardId, Float previousPositionNumber) {
        return jpaQueryFactory.select(status.statusNumber)
                .from(status)
                .where(status.board.boardId.eq(boardId))
                .where(status.statusNumber.lt(previousPositionNumber))
                .orderBy(status.statusNumber.desc())
                .fetchFirst();
    }

    @Override
    public float getNextStatusNumberByStatusId(Long boardId, Float previousPositionNumber) {
        return jpaQueryFactory.select(status.statusNumber)
                .from(status)
                .where(status.board.boardId.eq(boardId))
                .where(status.statusNumber.gt(previousPositionNumber))
                .orderBy(status.statusNumber.desc())
                .fetchFirst();
    }

    @Override
    public Float findFirstPositionStatusNumber(Long boardId) {
        return jpaQueryFactory.select(status.statusNumber)
                .from(status)
                .where(status.board.boardId.eq(boardId))
                .orderBy(status.statusNumber.asc())
                .fetchFirst();
    }
}
