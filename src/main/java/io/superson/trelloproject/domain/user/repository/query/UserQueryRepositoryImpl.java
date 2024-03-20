package io.superson.trelloproject.domain.user.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static io.superson.trelloproject.domain.comment.entity.QComment.comment;
import static io.superson.trelloproject.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public User findByUserAndComment(String userId, Long commentId) {
    return jpaQueryFactory.select(user)
            .from(user)
            .join(comment).on(comment.user.userId.eq(userId))
            .where(comment.commentId.eq(commentId))
            .fetchOne();
  }
}

