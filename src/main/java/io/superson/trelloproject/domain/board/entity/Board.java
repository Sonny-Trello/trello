package io.superson.trelloproject.domain.board.entity;


import io.superson.trelloproject.domain.board.dto.BoardRequestDto;
import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.global.util.Color;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "TB_BOARD")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

@SQLDelete(sql = "update TB_BOARD set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Color color;

    private String description;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    public Board(BoardRequestDto requestDto, User user) {
        this.name = requestDto.getName();
        this.color = requestDto.getColor();
        this.description = requestDto.getDescription();
    }

    public void update(BoardRequestDto requestDto) {
        this.name = requestDto.getName();
        this.color = requestDto.getColor();
        this.description = requestDto.getDescription();
    }
}
