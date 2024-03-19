package io.superson.trelloproject.domain.user.entity;

import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.user.dto.SignUpRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String userId;

  @Column(name = "email", nullable = false)
  @Email
  String email;
  @Column(name = "password", nullable = false)
  String password;

  public User(SignUpRequestDto requestDto, PasswordEncoder passwordEncoder) {
    this.email = requestDto.getEmail();
    this.password = passwordEncoder.encode(requestDto.getPassword());
  }
}
