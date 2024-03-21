package io.superson.trelloproject.domain.status.service;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.repository.command.board.BoardRepository;
import io.superson.trelloproject.domain.board.repository.query.BoardQueryRepository;
import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.dto.StatusRequestDto;
import io.superson.trelloproject.domain.status.dto.UpdateStatusResponseDto;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.status.repository.command.StatusRepository;
import io.superson.trelloproject.domain.status.repository.query.StatusQueryRepository;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.global.exception.UserPermissionException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {

    private final BoardQueryRepository boardQueryRepository;
    private final BoardRepository boardRepository;
    private final StatusRepository statusRepository;
    private final StatusQueryRepository statusQueryRepository;

    public CreateStatusResponseDto createStatus(User user, Long boardId, StatusRequestDto createStatusRequestDto) {
        validateUserIsBoardMember(user, boardId);

        Board board = boardRepository.findById(boardId);
        Status status = Status.builder().name(createStatusRequestDto.getName())
                .statusNumber((statusQueryRepository.getStatusCount(boardId) + 1) * 65536)
            .board(board).build();

        validateBoardId(status, boardId);
        return new CreateStatusResponseDto(statusRepository.save(user, status));
    }

    public UpdateStatusResponseDto updateStatus(User user, Long boardId, Long statusId, StatusRequestDto statusRequestDto) {
        Status status = statusRepository.findStatusOrElseThrow(statusId);

        validateUserIsBoardMember(user, boardId);
        validateBoardId(status, boardId);

        status.updateStatus(statusRequestDto);
        return new UpdateStatusResponseDto(status);
    }

    public void deleteStatus(User user, Long boardId, Long statusId) {
        Status status = statusRepository.findStatusOrElseThrow(statusId);

        validateUserIsBoardMember(user, boardId);
        validateBoardId(status, boardId);

        statusRepository.deleteById(statusId);
    }

    public void updateStatusNumber(User user, Long boardId, Long statusId, float previousPositionNumber) {
        // 이동하려는 status 이전 status float number로 카드 이전 조회
        // 이 두 카드의 statusNumber로 평균 값 구해서 status에 update
        Status status = statusRepository.findStatusOrElseThrow(statusId); // 이동할 현재 status
        Status newPositionOfpreviousStatus = statusQueryRepository.findPreviousStatus(boardId, previousPositionNumber);

        validateBoardId(status, boardId);
        validateBoardId(newPositionOfpreviousStatus, boardId);

        validateUserIsBoardMember(user, boardId);

        float nextPositionNumber = statusQueryRepository.getNextStatusNumberByStatusId(boardId, previousPositionNumber);
        float newPositionNumber = (previousPositionNumber + nextPositionNumber) / 2;

        status.updateStatusPosition(newPositionNumber);
    }

    private void validateUserIsBoardMember(User user, Long boardId) {
        List<User> userList = boardQueryRepository.findAllByBoardId(boardId);

        if (userList.stream().noneMatch(u -> u.getUserId().equals(user.getUserId()))) {
            throw new UserPermissionException("수정 권한이 없습니다.");
        }
    }

    private void validateBoardId(Status status, Long boardId) {
        if (!Objects.equals(status.getBoard().getBoardId(), boardId)) {
            throw new NoSuchElementException("해당 티켓 상태가 존재하지 않습니다");
        }
    }
}
