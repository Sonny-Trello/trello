package io.superson.trelloproject.domain.status.repository;

import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatusRepositoryImpl implements StatusRepository {

  private final StatusJpaRepository statusJpaRepository;

  @Override
  public CreateStatusResponseDto save(Status status) {
    return new CreateStatusResponseDto(statusJpaRepository.save(status));
  }
}
