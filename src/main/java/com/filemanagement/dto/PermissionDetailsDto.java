package com.filemanagement.dto;

import com.filemanagement.entity.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionDetailsDto {

    private String authority;

    private String authorityName;

    public static PermissionDetailsDto from(Permission permission) {
        PermissionDetailsDto dto = new PermissionDetailsDto();
        dto.setAuthority(permission.getAuthority());
        dto.setAuthorityName(permission.getAuthorityName());
        return dto;
    }
}
