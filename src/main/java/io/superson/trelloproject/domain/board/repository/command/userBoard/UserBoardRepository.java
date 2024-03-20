package io.superson.trelloproject.domain.board.repository.command.userBoard;

import io.superson.trelloproject.domain.board.entity.UserBoard;

public interface UserBoardRepository {

    UserBoard save(UserBoard userBoard);
}
