package io.superson.trelloproject.domain.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto<T> {

    private T data;

    public static <T> ResponseDto<T> of(T data) {
        return ResponseDto.<T>builder()
            .data(data)
            .build();
    }

}
