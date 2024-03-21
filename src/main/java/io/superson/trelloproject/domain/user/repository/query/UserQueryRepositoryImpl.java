package io.superson.trelloproject.domain.user.repository.query;

import static io.superson.trelloproject.domain.board.entity.Invite.QInvite.invite;
import static io.superson.trelloproject.domain.user.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import io.superson.trelloproject.domain.board.entity.Invite.InviteStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Invite> findAllByUserId(String userId) {
        return jpaQueryFactory.select(invite)
            .from(invite)
            .join(user).on(invite.userId.eq(user.userId))
            .where(
                invite.userId.eq(userId), invite.status.eq(InviteStatus.STANDBY)
            ).fetch();
    }
}

