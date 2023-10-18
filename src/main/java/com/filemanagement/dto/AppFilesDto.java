package com.filemanagement.dto;

import com.filemanagement.entity.AppFiles;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppFilesDto {

    private Long id;

    private byte[] binary;

    public AppFiles to() {
        AppFiles appFiles = new AppFiles();
        appFiles.setBinary(binary);
        return appFiles;
    }
}
