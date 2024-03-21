package io.superson.trelloproject.domain.board.repository.query;

import static io.superson.trelloproject.domain.board.entity.QBoard.board;
import static io.superson.trelloproject.domain.board.entity.QUserBoard.userBoard;
import static io.superson.trelloproject.domain.status.entity.QStatus.status;
import static io.superson.trelloproject.domain.ticket.entity.QTicket.ticket;
import static io.superson.trelloproject.domain.user.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.repository.query.vo.BoardDetailsVo;
import io.superson.trelloproject.domain.board.repository.query.vo.QBoardDetailsVo;
import io.superson.trelloproject.domain.board.repository.query.vo.QStatusesVo;
import io.superson.trelloproject.domain.board.repository.query.vo.QTicketsVo;
import io.superson.trelloproject.domain.board.repository.query.vo.StatusesVo;
import io.superson.trelloproject.domain.board.repository.query.vo.TicketsVo;
import io.superson.trelloproject.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findAllById(String userId) {
        return jpaQueryFactory
            .select(board)
            .from(userBoard, board)
            .where(userBoard.user.userId.eq(userId))
            .orderBy(board.modifiedAt.desc())
            .fetch();
    }

    // boardId로 조회한 board에 속해 있는 user 리스트
    @Override
    public List<User> findAllByBoardId(Long boardId) {
        return jpaQueryFactory.select(user)
            .from(user)
            .join(userBoard).on(user.userId.eq(userBoard.user.userId))
            .where(userBoard.board.boardId.eq(boardId))
            .fetch();
    }

    @Override
    public BoardDetailsVo findBoardDetailsById(Long id) {
        BoardDetailsVo boardDetailsVo = jpaQueryFactory
            .select(new QBoardDetailsVo(
                board.boardId,
                board.name,
                board.color,
                board.description
            ))
            .from(board)
            .where(board.boardId.eq(id))
            .fetchOne();

        List<StatusesVo> statusesVo = jpaQueryFactory
            .select(new QStatusesVo(
                status.statusId,
                status.name
            ))
            .from(status)
            .where(status.board.boardId.eq(id))
            .fetch();

        List<TicketsVo> ticketsVo = jpaQueryFactory
            .select(new QTicketsVo(
                ticket.ticketId,
                ticket.status.statusId,
                ticket.name,
                ticket.color,
                ticket.description,
                ticket.deadline
            ))
            .from(ticket)
            .where(ticket.board.boardId.eq(id))
            .fetch();

        boardDetailsVo.setStatuses(statusesVo);
        boardDetailsVo.setTickets(ticketsVo);

        return boardDetailsVo;
    }
}
