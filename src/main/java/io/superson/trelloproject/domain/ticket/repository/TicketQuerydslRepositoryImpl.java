package io.superson.trelloproject.domain.ticket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TicketQuerydslRepositoryImpl implements TicketQuerydslRepository {

    private final JPAQueryFactory queryFactory;


}
