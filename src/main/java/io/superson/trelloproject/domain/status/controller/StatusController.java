package io.superson.trelloproject.domain.status.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.status.dto.CreateStatusRequestDto;
import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.service.StatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<ResponseDto<CreateStatusResponseDto>> createStatus(@RequestBody @Valid CreateStatusRequestDto createStatusRequestDto) {
        CreateStatusResponseDto createStatusResponseDto = statusService.createStatus(createStatusRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ResponseDto.<CreateStatusResponseDto>builder()
                .data(createStatusResponseDto)
                .build());
    }
}
