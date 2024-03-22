package io.superson.trelloproject.domain.status.repository.query;


import io.superson.trelloproject.domain.status.entity.Status;

import java.util.List;
import java.util.Optional;

public interface StatusQueryRepository {

    List<String> findAllByBoardId(Long boardId);

    Long getStatusCount(Long boardId);

    Optional<Status> findPreviousStatus(Long boardId, float previousPositionNumber);

    Optional<Status> findFollowingStatus(Long boardId, float previousPositionNumber);

    float getPreviousStatusNumberByStatusId(Long boardId, float previousPositionNumber);

    float getNextStatusNumberByStatusId(Long boardId, float previousPositionNumber);

    float findFirstPositionStatusNumber(Long boardId);
}
