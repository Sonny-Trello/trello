package io.superson.trelloproject.domain.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ExceptionDto {

  private HttpStatus status;
  private String message;

}
