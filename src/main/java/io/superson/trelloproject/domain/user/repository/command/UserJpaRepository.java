package io.superson.trelloproject.domain.user.repository.command;

import io.superson.trelloproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
