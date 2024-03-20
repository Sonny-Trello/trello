package io.superson.trelloproject.domain.status.repository.query;

import static io.superson.trelloproject.domain.status.entity.QStatus.status;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.status.entity.Status;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
