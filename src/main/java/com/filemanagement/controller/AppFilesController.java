package com.filemanagement.controller;


import com.filemanagement.dto.AppItemDto;
import com.filemanagement.entity.AppFiles;
import com.filemanagement.service.AppFilesService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.filemanagement.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping(path = "files")
@Tag(name = "files api")
@RequiredArgsConstructor
public class AppFilesController {

    private final AppFilesService appFilesService;

    @PostMapping("/{spaceId}")
    @PreAuthorize("hasAuthority('CREATE_FILES')")
    @Operation(summary = "uploading a file", description = "uploading a file")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AppItemDto.class))
    })
    public ResponseEntity<JSONObject> upload(@RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Long spaceId) throws IOException {
        AppFiles appFiles = appFilesService.save(file, spaceId);
        return ok(success(appFiles).getJson());
    }
}
