package io.superson.trelloproject.domain.status.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.dto.StatusRequestDto;
import io.superson.trelloproject.domain.status.service.StatusService;
import io.superson.trelloproject.global.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<ResponseDto<CreateStatusResponseDto>> createStatus(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid StatusRequestDto statusRequestDto) {
        CreateStatusResponseDto createStatusResponseDto = statusService.createStatus(userDetails.getUser(), statusRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ResponseDto.<CreateStatusResponseDto>builder()
                .data(createStatusResponseDto)
                .build());
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<ResponseDto<Void>> updateStatus(@PathVariable Long statusId, @RequestBody @Valid StatusRequestDto statusRequestDto) {
        statusService.updateStatus(statusId, statusRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ResponseDto.<Void>builder().build());
    }
}
