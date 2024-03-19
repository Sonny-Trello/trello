package io.superson.trelloproject.domain.board.service;

import io.superson.trelloproject.domain.board.dto.BoardRequestDto;
import io.superson.trelloproject.domain.board.dto.BoardResponseDto;
import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.repository.command.BoardRepository;
import io.superson.trelloproject.domain.board.repository.query.BoardQueryRepository;
import io.superson.trelloproject.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

    public BoardResponseDto createBoard(User user, BoardRequestDto requestDto) {
        Board board = boardRepository.save(new Board(requestDto, user));
        return new BoardResponseDto(board);
    }

    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id);
        board.update(requestDto);

        return new BoardResponseDto(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.findById(id);
        boardRepository.deleteById(id);
    }

    public List<BoardResponseDto> getBoards(User user) {
        List<Board> boards = boardQueryRepository.findAllById(user.getUserId());
        return boards.stream().map(BoardResponseDto::new).toList();
    }
}
