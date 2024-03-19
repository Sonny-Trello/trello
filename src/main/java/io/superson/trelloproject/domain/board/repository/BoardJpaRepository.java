package io.superson.trelloproject.domain.board.repository;

import io.superson.trelloproject.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board, Long>{

}