package com.filemanagement.dto;

import com.filemanagement.entity.Permission;
import com.poiji.annotation.ExcelCellName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PermissionImportDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelCellName("Authority")
    private String authority;

    @ExcelCellName("Authority Name")
    private String authorityName;

    public Permission toImportData() {
        Permission permission = new Permission();
        permission.setAuthority(authority);
        permission.setAuthorityName(authorityName);
        return permission;
    }
}