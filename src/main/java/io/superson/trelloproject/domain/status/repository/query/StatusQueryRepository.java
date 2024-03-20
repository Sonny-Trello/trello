package io.superson.trelloproject.domain.status.repository.query;


import io.superson.trelloproject.domain.status.entity.Status;
import java.util.List;

public interface StatusQueryRepository {

    List<String> findAllByBoardId(Long boardId);

}
