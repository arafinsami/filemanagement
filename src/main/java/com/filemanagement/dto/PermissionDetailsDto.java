package com.filemanagement.dto;

import com.filemanagement.entity.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionDetailsDto {

    private String permissionLevel;

    public static PermissionDetailsDto from(Permission permission) {
        PermissionDetailsDto dto = new PermissionDetailsDto();
        dto.setPermissionLevel(permission.getPermissionLevel());
        return dto;
    }
}
