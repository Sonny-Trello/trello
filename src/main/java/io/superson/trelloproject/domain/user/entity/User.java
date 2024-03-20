package io.superson.trelloproject.domain.user.entity;

import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.user.dto.SignUpRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_user")
@SQLDelete(sql = "update tb_user set deleted_at = NOW() where user_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    public User(SignUpRequestDto requestDto, PasswordEncoder passwordEncoder) {
        this.email = requestDto.getEmail();
        this.password = passwordEncoder.encode(requestDto.getPassword());
    }
}
