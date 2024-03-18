package io.superson.trelloproject.global.exception;

import io.superson.trelloproject.domain.common.dto.ExceptionDto;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler({IllegalArgumentException.class,
      MethodArgumentNotValidException.class})
  public ResponseEntity<ExceptionDto> handleException(IllegalArgumentException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder()
        .status(HttpStatus.BAD_REQUEST)
        .message(e.getMessage())
        .build();
    return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler({NullPointerException.class,
      NoSuchElementException.class})
  public ResponseEntity<ExceptionDto> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
        .body(
            ExceptionDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build());
  }
}
