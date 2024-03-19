package io.superson.trelloproject.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String userId;

  String email;
  String password;

}
