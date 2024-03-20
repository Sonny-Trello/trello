package io.superson.trelloproject.domain.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto<T> {

    private T data;

}
