package com.filemanagement.service;


import com.filemanagement.entity.AppFiles;
import com.filemanagement.model.Action;
import com.filemanagement.repository.AppFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.valueOf;

@Service
@RequiredArgsConstructor
public class AppFilesService {

    private final ActionLogService actionLogService;

    private final AppFilesRepository appFilesRepository;

    @Transactional
    public AppFiles save(AppFiles appFiles, Action action, String comments) {
        AppFiles af = appFilesRepository.save(appFiles);
        actionLogService.publishActivity(action, valueOf(af.getId()), comments);
        return af;
    }
}
