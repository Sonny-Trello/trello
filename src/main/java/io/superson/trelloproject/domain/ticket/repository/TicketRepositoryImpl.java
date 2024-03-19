package io.superson.trelloproject.domain.ticket.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TicketRepositoryImpl implements TicketRepository {

    private final TicketQuerydslJpaRepository repository;

}
