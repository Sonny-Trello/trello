package io.superson.trelloproject.domain.status.repository.query;


import io.superson.trelloproject.domain.status.dto.UpdateStatusNumberResponseDto;
import io.superson.trelloproject.domain.status.entity.Status;

import java.util.Optional;

public interface StatusQueryRepository {

    Long getStatusCountByBoardId(Long boardId);

    Optional<Status> findPreviousStatus(Long boardId, Float previousPositionNumber);

    Optional<Status> findFollowingStatus(Long boardId, Float previousPositionNumber);

    UpdateStatusNumberResponseDto findFirstOrFinalPositionStatusNumber(Long boardId, boolean isAsc);
}
