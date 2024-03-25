package io.superson.trelloproject.domain.status.service;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.repository.command.board.BoardRepository;
import io.superson.trelloproject.domain.board.repository.query.BoardQueryRepository;
import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.dto.StatusRequestDto;
import io.superson.trelloproject.domain.status.dto.UpdateStatusNumberResponseDto;
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
                .statusNumber((statusQueryRepository.getStatusCountByBoardId(boardId) + 1) * 65536)
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

    public UpdateStatusNumberResponseDto updateStatusNumber(User user, Long boardId, Long statusId, Float previousPositionNumber) {
        Status status = statusRepository.findStatusOrElseThrow(statusId); // 이동할 현재 status

        // previousPositionNumber null (맨 앞 이동)
        if (previousPositionNumber == null) {
            status.updateStatusPosition(toStatusNumberBuilder(boardId, true));
            return new UpdateStatusNumberResponseDto(statusId, status.getStatusNumber());
        }

        Optional<Status> newPositionPreviousStatus = statusQueryRepository.findPreviousStatus(boardId, previousPositionNumber);
        Optional<Status> newPositionFollowingStatus = statusQueryRepository.findFollowingStatus(boardId, previousPositionNumber);

        validateBoardId(status, boardId);
        validateUserIsBoardMember(user, boardId);

        // 이동할 위치가 맨 앞 : 카드 이동 중 newPositionPreviousStatus 삭제 등..
        if (newPositionPreviousStatus.isEmpty()) {
            status.updateStatusPosition(toStatusNumberBuilder(boardId, true));
            return new UpdateStatusNumberResponseDto(statusId, status.getStatusNumber());
        }

        // 위치 이동 없음
        if (previousPositionNumber == newPositionPreviousStatus.get().getStatusNumber()) {
            status.updateStatusPosition(status.getStatusNumber());
            return new UpdateStatusNumberResponseDto(statusId, status.getStatusNumber());
        }

        // 이동할 위차가 맨 뒤
        if (newPositionFollowingStatus.isEmpty()) {
            status.updateStatusPosition(toStatusNumberBuilder(boardId, false));
            return new UpdateStatusNumberResponseDto(statusId, status.getStatusNumber());
        }

        // 이동할 위치 앞 뒤 상태 존재
        float nextPositionNumber = newPositionFollowingStatus.get().getStatusNumber();
        float newPositionNumber = (previousPositionNumber + nextPositionNumber) / 2;

        status.updateStatusPosition(newPositionNumber);
        return new UpdateStatusNumberResponseDto(statusId, status.getStatusNumber());
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

    private float toStatusNumberBuilder(Long boardId, boolean isAsc) {
        return isAsc ? statusQueryRepository.findFirstOrFinalPositionStatusNumber(boardId, true).getCurrentStatusNumber() / 2
                : statusQueryRepository.findFirstOrFinalPositionStatusNumber(boardId, false).getCurrentStatusNumber() + 65536;
    }
}
