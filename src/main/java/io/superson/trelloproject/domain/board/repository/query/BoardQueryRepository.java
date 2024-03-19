package io.superson.trelloproject.domain.board.repository.query;

import io.superson.trelloproject.domain.board.entity.Board;
import java.util.List;

public interface BoardQueryRepository {

    List<Board> findAllById(String userId);
}
