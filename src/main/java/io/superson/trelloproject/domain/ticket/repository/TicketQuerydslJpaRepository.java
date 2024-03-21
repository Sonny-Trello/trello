package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketQuerydslJpaRepository extends
    TicketQuerydslRepository, JpaRepository<Ticket, Long> {

}
