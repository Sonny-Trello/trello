package io.superson.trelloproject.domain.user.entity;

import io.superson.trelloproject.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
//@Setter
public class User extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID userId;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column
  private Timestamp deletedAt;
}
