package io.superson.trelloproject.domain.status.service;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.status.dto.StatusRequestDto;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.status.repository.StatusRepository;
import io.superson.trelloproject.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {

    private final StatusRepository statusRepository;

    public void createStatus(User user, Long boardId, StatusRequestDto createStatusRequestDto) {
        Board board = new Board();
        Status status = Status.builder().name(createStatusRequestDto.getName())
                .board(board).build();
        List<Long> boardIdList = user.getUserBoards().stream()
                .map(UserBoard::getUserBoardId)
                .toList();
        if (boardIdList.contains(status.getBoard().getBoardId())) {
            statusRepository.save(user, status);
        }
    }

    public void updateStatus(User user, Long boardId, Long statusId, StatusRequestDto statusRequestDto) {
        Status status = statusRepository.findStatusOrElseThrow(statusId);
        // user가 갖고 있는 boardIdList에 현재 status를 생성한 사람의 boardId가 있는지 확인
        List<Long> boardIdList = user.getUserBoards().stream()
                .map(UserBoard::getUserBoardId)
                .toList();
        if (Objects.equals(status.getBoard().getBoardId(), boardId) && boardIdList.contains(status.getBoard().getBoardId())) {
            status.updateStatus(statusRequestDto);
        }
    }

    public void deleteStatus(User user, Long boardId, Long statusId) {
        Status status = statusRepository.findStatusOrElseThrow(statusId);
        List<Long> boardIdList = user.getUserBoards().stream()
                .map(UserBoard::getUserBoardId)
                .toList();
        if (Objects.equals(status.getBoard().getBoardId(), boardId) && boardIdList.contains(status.getBoard().getBoardId())) {
            statusRepository.deleteById(statusId);
        }
    }
}
