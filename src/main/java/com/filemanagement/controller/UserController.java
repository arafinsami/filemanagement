package com.filemanagement.controller;


import com.filemanagement.dto.UserDto;
import com.filemanagement.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.filemanagement.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "user api")
@RequiredArgsConstructor
public class UserController {

    private final AppUserService appUserService;

    @GetMapping("/lists")
    @PreAuthorize("hasAuthority('USER_DETAILS')")
    @Operation(summary = "show lists of users", description = "lists of users")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
    })
    public ResponseEntity<JSONObject> findAll() {
        List<UserDto> users = appUserService.finaAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
        return ok(success(users).getJson());
    }
}
