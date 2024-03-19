package io.superson.trelloproject.domain.board.repository;

import io.superson.trelloproject.domain.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{
    private final BoardJpaRepository boardJpaRepository;

    @Override
    public Board save(Board board) {
        return boardJpaRepository.save(board);
    }

    @Override
    public void delete(Board board) {
        boardJpaRepository.delete(board);
    }

    @Override
    public Board findById(Long id) {
        return boardJpaRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당하는 보드가 없습니다."));
    }

    @Override
    public void deleteById(Long id) {
        boardJpaRepository.deleteById(id);
    }


}
