package io.superson.trelloproject.domain.status.repository;

import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class StatusRepositoryImpl implements StatusRepository {

    private final StatusJpaRepository statusJpaRepository;

    @Override
    public CreateStatusResponseDto save(Status status) {
        return new CreateStatusResponseDto(statusJpaRepository.save(status));
    }

    @Override
    public Status findStatusOrElseThrow(Long statusId) {
        return statusJpaRepository.findById(statusId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
    }

    @Override
    public void deleteById(Long statusId) {

    }
}
