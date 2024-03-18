package io.superson.trelloproject.domain.status.repository;

import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.entity.Status;

public interface StatusRepository {

    CreateStatusResponseDto save(Status status);
}
