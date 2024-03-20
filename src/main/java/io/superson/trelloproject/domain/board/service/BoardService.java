package io.superson.trelloproject.domain.board.service;

import io.superson.trelloproject.domain.board.dto.BoardRequestDto;
import io.superson.trelloproject.domain.board.dto.BoardResponseDto;
import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.board.repository.command.BoardRepository;
import io.superson.trelloproject.domain.board.repository.command.UserBoardRespository;
import io.superson.trelloproject.domain.board.repository.query.BoardQueryRepository;
import io.superson.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final UserBoardRespository userBoardRespository;

    public BoardResponseDto createBoard(User user, BoardRequestDto requestDto) {
        Board board = boardRepository.save(new Board(requestDto, user));
        userBoardRespository.save(new UserBoard(user, board));
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

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards(User user) {
        System.out.println("user.getUserId() = " + user.getUserId());
        List<Board> boards = boardQueryRepository.findAllById(user.getUserId());
        boards.forEach(board -> System.out.println("board.getBoardId() = " + board.getBoardId()));
        System.out.println("size : " + boards.size());
        return boards.stream().map(BoardResponseDto::new).toList();
    }
}
