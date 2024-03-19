package io.superson.trelloproject.domain.board.repository.command;

import io.superson.trelloproject.domain.board.entity.UserBoard;

public interface UserBoardRespository {
    UserBoard save(UserBoard userBoard);
}
