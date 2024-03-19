package io.superson.trelloproject.domain.board.repository.command;

import io.superson.trelloproject.domain.board.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardJpaRepository extends JpaRepository<UserBoard, Long> {

}
