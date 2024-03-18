package io.superson.trelloproject.domain.status.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatusRepositoryImpl implements StatusRepository {

  private final StatusJpaRepository statusJpaRepository;

}
