package io.superson.trelloproject.domain.user.repository.command;

import io.superson.trelloproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserJpaRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
