package io.superson.trelloproject.domain.board.repository.command;

import io.superson.trelloproject.domain.board.entity.UserBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserBoardRepositoryImpl implements UserBoardRespository {
    private final UserBoardJpaRepository userBoardJpaRepository;

    @Override
    public UserBoard save(UserBoard userBoard) {
        return userBoardJpaRepository.save(userBoard);
    }
}
