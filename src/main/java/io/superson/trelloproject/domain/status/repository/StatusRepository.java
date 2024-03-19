package io.superson.trelloproject.domain.status.repository;

import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.user.entity.User;

public interface StatusRepository {

    void save(User user, Status status);

    Status findStatusOrElseThrow(Long statusId);

    void deleteById(Long statusId);
}
