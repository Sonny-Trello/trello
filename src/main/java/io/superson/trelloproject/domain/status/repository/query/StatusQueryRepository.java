package io.superson.trelloproject.domain.status.repository.query;


import io.superson.trelloproject.domain.status.entity.Status;

import java.util.List;
import java.util.Optional;

public interface StatusQueryRepository {

    List<String> findAllByBoardId(Long boardId);

    Long getStatusCount(Long boardId);

    Optional<Status> findPreviousStatus(Long boardId, Float previousPositionNumber);

    Optional<Status> findFollowingStatus(Long boardId, Float previousPositionNumber);

    float getPreviousStatusNumberByStatusId(Long boardId, Float previousPositionNumber);

    float getNextStatusNumberByStatusId(Long boardId, Float previousPositionNumber);

    Float findFirstPositionStatusNumber(Long boardId);
}
