package io.superson.trelloproject.domain.status.repository.command;

import io.superson.trelloproject.domain.status.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusJpaRepository extends JpaRepository<Status, Long> {
}
