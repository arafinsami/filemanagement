package com.filemanagement.dto;

import com.filemanagement.entity.Permission;
import com.poiji.annotation.ExcelCellName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class PermissionImportDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelCellName("Permission Level")
    private String permissionLevel;

    public Permission toImportData() {
        Permission permission = new Permission();
        permission.setPermissionLevel(permissionLevel);
        return permission;
    }
}