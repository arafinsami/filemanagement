package com.filemanagement.service;

import com.filemanagement.entity.AppItem;
import com.filemanagement.model.Action;
import com.filemanagement.repository.AppItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.valueOf;

@Service
@RequiredArgsConstructor
public class AppItemService {

    private final ActionLogService actionLogService;

    private final AppItemRepository appItemRepository;

    @Transactional
    public AppItem save(AppItem appItem, Action action, String comments) {
        AppItem ai = appItemRepository.save(appItem);
        actionLogService.publishActivity(action, valueOf(ai.getId()), comments);
        return ai;
    }
}
