package io.superson.trelloproject.domain.status.repository.command;

import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.user.entity.User;
import java.util.List;

public interface StatusRepository {

    Status save(User user, Status status);

    Status findStatusOrElseThrow(Long statusId);

    void deleteById(Long statusId);

}
