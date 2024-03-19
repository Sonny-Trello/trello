package io.superson.trelloproject.domain.user.entity;

import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.user.dto.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_user")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @OneToMany(mappedBy = "user")
    private List<UserBoard> userBoards;

    public User(SignUpRequestDto requestDto, PasswordEncoder passwordEncoder) {
        this.email = requestDto.getEmail();
        this.password = passwordEncoder.encode(requestDto.getPassword());
    }
}
