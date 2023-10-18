package com.filemanagement.controller;


import com.filemanagement.dto.AppItemDto;
import com.filemanagement.entity.AppItem;
import com.filemanagement.exception.ResourceNotFoundException;
import com.filemanagement.service.AppItemService;
import com.filemanagement.validator.AppItemValidator;
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

import static com.filemanagement.exception.ApiError.fieldError;
import static com.filemanagement.utils.ResponseBuilder.error;
import static com.filemanagement.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping(path = "space")
@Tag(name = "space api")
@RequiredArgsConstructor
public class AppItemController {

    private final AppItemService appItemService;

    private final AppItemValidator appItemValidator;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('CREATE_SPACE')")
    @Operation(summary = "creating a space", description = "creating a space")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AppItemDto.class))
    })
    public ResponseEntity<JSONObject> save(@Valid @RequestBody AppItemDto dto,
                                           BindingResult bindingResult) {
        ValidationUtils.invokeValidator(appItemValidator, dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        AppItem request = dto.to();
        AppItem appItem = appItemService.save(request);
        return ok(success(AppItemDto.from(appItem)).getJson());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_SPACE')")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AppItemDto.class))
    })
    @Operation(summary = "updating a space", description = "updating a updating")
    public ResponseEntity<JSONObject> update(@Valid @RequestBody AppItemDto dto,
                                             BindingResult bindingResult) {
        ValidationUtils.invokeValidator(appItemValidator, dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        AppItem request = appItemService.findById(dto.getId()).orElseThrow(ResourceNotFoundException::new);
        dto.update(request);
        AppItem appItem = appItemService.update(request, dto.getName());
        return ok(success(AppItemDto.from(appItem)).getJson());
    }

    @GetMapping("/view/{spaceId}")
    @PreAuthorize("hasAuthority('VIEW_SPACE')")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AppItemDto.class))
    })
    @Operation(summary = "view a space", description = "view a updating")
    public ResponseEntity<JSONObject> viewSpace(@PathVariable Long spaceId) {
        AppItem appItem = appItemService.findById(spaceId).orElseThrow(ResourceNotFoundException::new);
        return ok(success(AppItemDto.from(appItem)).getJson());
    }
}
