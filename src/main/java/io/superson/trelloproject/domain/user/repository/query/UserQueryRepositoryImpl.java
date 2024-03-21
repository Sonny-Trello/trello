package io.superson.trelloproject.domain.user.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import io.superson.trelloproject.domain.board.entity.Invite.InviteStatus;
import io.superson.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.superson.trelloproject.domain.board.entity.Invite.QInvite.invite;
import static io.superson.trelloproject.domain.comment.entity.QComment.comment;
import static io.superson.trelloproject.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Invite> findAllInviteByUserId(String userId) {
        return jpaQueryFactory.select(invite)
                .from(invite)
                .join(user).on(invite.userId.eq(user.userId))
                .where(
                        invite.userId.eq(userId), invite.status.eq(InviteStatus.STANDBY)
                ).fetch();
    }

    @Override
    public Optional<User> findByUserAndComment(Long commentId) {
        return Optional.ofNullable(jpaQueryFactory.select(user)
                .from(user)
                .join(comment).on(comment.user.userId.eq(user.userId))
                .where(comment.commentId.eq(commentId))
                .fetchOne());
    }
}

