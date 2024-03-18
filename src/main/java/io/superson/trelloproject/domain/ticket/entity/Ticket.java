package io.superson.trelloproject.domain.ticket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Ticket {

  @Id
  Long ticketId;

}
