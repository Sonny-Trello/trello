package io.superson.trelloproject.domain.status.service;

import io.superson.trelloproject.domain.status.dto.StatusRequestDto;
import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.status.repository.StatusRepository;
import io.superson.trelloproject.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {

    private final StatusRepository statusRepository;

    public CreateStatusResponseDto createStatus(User user, StatusRequestDto createStatusRequestDto) {
        Status status = Status.builder().name(createStatusRequestDto.getName()).build();
        return statusRepository.save(status);
    }

    public void updateStatus(Long statusId, StatusRequestDto statusRequestDto) {
        Status status = statusRepository.findById(statusId);
        status.updateStatus(statusRequestDto);
    }
}
