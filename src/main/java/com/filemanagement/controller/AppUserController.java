package com.filemanagement.controller;


import com.filemanagement.dto.AppUserDto;
import com.filemanagement.entity.AppUser;
import com.filemanagement.exception.ResourceNotFoundException;
import com.filemanagement.service.AppUserService;
import com.filemanagement.validator.AppUserValidator;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.filemanagement.exception.ApiError.fieldError;
import static com.filemanagement.utils.ResponseBuilder.error;
import static com.filemanagement.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "user api")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    private final AppUserValidator appUserValidator;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('SAVE_USER')")
    @Operation(summary = "saving a user", description = "saving a user")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AppUserDto.class))
    })
    public ResponseEntity<JSONObject> save(@Valid @RequestBody AppUserDto dto,
                                           BindingResult bindingResult) {
        ValidationUtils.invokeValidator(appUserValidator, dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        AppUser request = dto.to();
        AppUser appUser = appUserService.save(request);
        return ok(success(AppUserDto.from(appUser)).getJson());
    }


    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @Operation(summary = "updating a user", description = "updating a user")
    public ResponseEntity<JSONObject> update(@Valid @RequestBody AppUserDto dto,
                                             BindingResult bindingResult) {
        ValidationUtils.invokeValidator(appUserValidator, dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        AppUser request = appUserService.findById(dto.getId()).orElseThrow(ResourceNotFoundException::new);
        dto.update(request);
        AppUser appUser = appUserService.update(request);
        return ok(success(AppUserDto.from(appUser)).getJson());
    }
    
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('READ_USER')")
    @Operation(summary = "show lists of users", description = "lists of users")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AppUserDto.class))
    })
    public ResponseEntity<JSONObject> getAppUsers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> map = appUserService.getAppUsers(page, size);
        List<AppUser> appUsers = (List<AppUser>) map.get("lists");
        List<AppUserDto> users = appUsers.stream()
                .map(AppUserDto::from)
                .collect(Collectors.toList());
        return ok(success(users).getJson());
    }
}
