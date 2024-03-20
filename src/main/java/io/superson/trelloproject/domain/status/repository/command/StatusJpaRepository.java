package io.superson.trelloproject.domain.status.repository.command;

import io.superson.trelloproject.domain.status.entity.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusJpaRepository extends JpaRepository<Status, Long> {

}
