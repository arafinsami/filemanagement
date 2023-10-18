package com.filemanagement.service;


import com.filemanagement.entity.AppFiles;
import com.filemanagement.entity.AppItem;
import com.filemanagement.exception.ResourceNotFoundException;
import com.filemanagement.repository.AppFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.filemanagement.model.Action.SAVE;
import static com.filemanagement.model.ModuleName.FILE;
import static com.filemanagement.utils.StringUtils.objectToJson;

@Service
@RequiredArgsConstructor
public class AppFilesService {

    private final ActionLogService actionLogService;

    private final AppFilesRepository appFilesRepository;

    private final AppItemService appItemService;

    @Transactional
    public AppFiles save(MultipartFile file, Long spaceId) throws IOException {
        AppItem appItem = appItemService.findById(spaceId).orElseThrow(ResourceNotFoundException::new);
        AppFiles files = new AppFiles();
        files.setBinary(file.getBytes());
        files.setAppItem(appItem);
        AppFiles af = appFilesRepository.save(files);
        actionLogService.publishActivity(
                SAVE,
                FILE,
                String.valueOf(af.getId()),
                objectToJson(af)
        );
        return af;
    }
}
