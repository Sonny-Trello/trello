package io.superson.trelloproject.domain.user.dto;

import io.superson.trelloproject.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private String userId;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserResponseDto(User foundUser) {
        this.userId = foundUser.getUserId();
        this.email = foundUser.getEmail();
        this.createdAt = foundUser.getCreatedAt();
        this.modifiedAt = foundUser.getModifiedAt();
    }
}
