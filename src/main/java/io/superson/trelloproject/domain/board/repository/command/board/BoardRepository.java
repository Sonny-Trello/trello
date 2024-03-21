package io.superson.trelloproject.domain.board.repository.command.board;

import io.superson.trelloproject.domain.board.entity.Board;

public interface BoardRepository {
    Board save(Board board);

    Board findById(Long id);

    void deleteById(Long id);


}
