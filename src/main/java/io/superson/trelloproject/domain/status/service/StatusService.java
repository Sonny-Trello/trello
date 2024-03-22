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
import java.util.Optional;

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
                .statusNumber(toStatusNumberBuilder(boardId))
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

    public void updateStatusNumber(User user, Long boardId, Long statusId, Float previousPositionNumber) {
        Status status = statusRepository.findStatusOrElseThrow(statusId); // 이동할 현재 status
        Optional<Status> newPositionPreviousStatus = statusQueryRepository.findPreviousStatus(boardId, previousPositionNumber);
        Optional<Status> newPositionFollowingStatus = statusQueryRepository.findFollowingStatus(boardId, previousPositionNumber);

        validateBoardId(status, boardId);
        validateUserIsBoardMember(user, boardId);

        // 이동할 위치가 맨 앞 : 파라미터가 null로 들어올 수 없기 때문에 후에 프론트 처리 예정
        if (newPositionPreviousStatus.isEmpty()) {
            status = Status.builder().statusNumber(toFirstPositionStatusNumberBuilder(boardId)).build();
            return;
        }

        // 위치 이동 없음
        if (previousPositionNumber == statusQueryRepository.getPreviousStatusNumberByStatusId(boardId, previousPositionNumber)) {
            status.updateStatusPosition(status.getStatusNumber());
            return;
        }

        // 이동할 위차가 맨 뒤
        if (newPositionFollowingStatus.isEmpty()) {
            status = Status.builder().statusNumber(toStatusNumberBuilder(boardId)).build();
            return;
        }

        // 이동할 위치 앞 뒤 상태 존재
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

    private float toStatusNumberBuilder(Long boardId) {
        return (statusQueryRepository.getStatusCount(boardId) + 1) * 65536;
    }

    private float toFirstPositionStatusNumberBuilder(Long boardId) {
        return statusQueryRepository.findFirstPositionStatusNumber(boardId) / 2;
    }
}
