package com.filemanagement.controller;

import com.filemanagement.dto.PermissionImportDto;
import com.filemanagement.entity.Permission;
import com.filemanagement.service.PermissionService;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Permission Data Upload")
@RequestMapping(path = "permission-data-upload")
public class PermissionUploadController {

    private final PermissionService permissionService;

    @PostMapping
    @Operation(summary = "Permission Data Upload to the system", description = "Permission Data Upload to the system")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PermissionImportDto.class))})
    public ResponseEntity<?> save(@RequestPart("file") MultipartFile file) throws IOException {
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        List<PermissionImportDto> items = Poiji.fromExcel(file.getInputStream(),
                PoijiExcelType.valueOf(extension.toUpperCase()),
                PermissionImportDto.class,
                PoijiOptions.PoijiOptionsBuilder.settings().preferNullOverDefault(true).build()
        );

        List<Permission> permissions = items.stream().map(PermissionImportDto::toImportData).collect(Collectors.toList());
        permissionService.saveAll(permissions);
        return ResponseEntity.ok("Uploaded");
    }
}