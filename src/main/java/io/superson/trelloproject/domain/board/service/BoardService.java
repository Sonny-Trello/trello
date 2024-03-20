package io.superson.trelloproject.domain.board.service;

import io.superson.trelloproject.domain.board.dto.BoardRequestDto;
import io.superson.trelloproject.domain.board.dto.BoardResponseDto;
import io.superson.trelloproject.domain.board.dto.InviteRequestDto;
import io.superson.trelloproject.domain.board.dto.InviteResponseDto;
import io.superson.trelloproject.domain.board.dto.InviteResultRequestDto;
import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.board.repository.command.BoardRepository;
import io.superson.trelloproject.domain.board.repository.command.UserBoardRepository;
import io.superson.trelloproject.domain.board.repository.command.invite.InviteRepository;
import io.superson.trelloproject.domain.board.repository.query.BoardQueryRepository;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.domain.user.repository.command.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final UserBoardRepository userBoardRepository;
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;

    public BoardResponseDto createBoard(User user, BoardRequestDto requestDto) {
        Board board = boardRepository.save(new Board(requestDto, user));
        userBoardRepository.save(new UserBoard(user, board));
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
        List<Board> boards = boardQueryRepository.findAllById(user.getUserId());

        return boards.stream().map(BoardResponseDto::new).toList();
    }

    public InviteResponseDto inviteBoard(Long id, InviteRequestDto requestDto) {
        Board board = boardRepository.findById(id);

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
            () -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        Invite invite = new Invite(board, user);
        inviteRepository.save(invite);

        return new InviteResponseDto(invite);
    }

    public void inviteResult(Long id, InviteResultRequestDto requestDto) {
        Invite invite = inviteRepository.findById(id);
        invite.update(requestDto.getStatus());

        if (requestDto.getStatus().equals("ACCEPTED")) {
            User user = userRepository.findById(invite.getUserId()).orElseThrow();
            Board board = boardRepository.findById(invite.getBoardId());
            userBoardRepository.save(new UserBoard(user, board));
        }
    }
}
