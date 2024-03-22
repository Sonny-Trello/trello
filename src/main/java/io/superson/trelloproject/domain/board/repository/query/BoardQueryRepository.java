package io.superson.trelloproject.domain.board.repository.query;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.repository.query.vo.BoardDetailsVo;
import io.superson.trelloproject.domain.user.entity.User;

import java.util.List;

public interface BoardQueryRepository {

    List<Board> findAllById(String userId);

    List<User> findAllByBoardId(Long boardId);

    BoardDetailsVo findBoardDetailsById(Long id);
}
