package io.superson.trelloproject.domain.user.repository.command;

import io.superson.trelloproject.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public User save(User save) {
        return userJpaRepository.save(save);
    }

    @Override
    public Optional<User> findById(String userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public void deleteById(String userId) {
        userJpaRepository.deleteById(userId);
    }

    @Override
    public List<User> findUsersByEmails(List<String> assigneeEmails) {
        return userJpaRepository.findAllByEmailIn(assigneeEmails);
    }

}
