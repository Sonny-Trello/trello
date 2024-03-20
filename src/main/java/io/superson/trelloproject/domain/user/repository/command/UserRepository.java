package io.superson.trelloproject.domain.user.repository.command;

import io.superson.trelloproject.domain.user.entity.User;
import java.util.Optional;

public interface UserRepository {

    //함수정의
    Optional<User> findByEmail(String email);

    User save(User save);

    Optional<User> findById(String userId);

    void deleteById(String userId);
}
