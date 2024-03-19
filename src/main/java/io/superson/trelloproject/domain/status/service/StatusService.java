package io.superson.trelloproject.domain.status.service;

import io.superson.trelloproject.domain.status.dto.CreateStatusRequestDto;
import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.status.repository.StatusRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {

    private final StatusRepository statusRepository;

    public CreateStatusResponseDto createStatus(CreateStatusRequestDto createStatusRequestDto) {
        Status status = Status.builder().name(createStatusRequestDto.getName()).build();
        return statusRepository.save(status);
    }
}
