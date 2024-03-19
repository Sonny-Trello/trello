package io.superson.trelloproject.domain.board.repository;

import io.superson.trelloproject.domain.board.entity.Board;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);
    void delete(Board board);
    Board findById(Long id);
    void deleteById(Long id);

}
