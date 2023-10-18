package com.filemanagement.service;

import com.filemanagement.dto.AppItemDto;
import com.filemanagement.entity.AppItem;
import com.filemanagement.helper.AppItemHelper;
import com.filemanagement.model.RecordStatus;
import com.filemanagement.repository.AppItemRepository;
import com.filemanagement.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.filemanagement.model.Action.SAVE;
import static com.filemanagement.model.Action.UPDATE;
import static com.filemanagement.model.ModuleName.SPACE;
import static com.filemanagement.utils.StringUtils.objectToJson;

@Service
@RequiredArgsConstructor
public class AppItemService extends FileUtils {

    private final ActionLogService actionLogService;

    private final AppItemRepository appItemRepository;

    private final AppItemHelper appItemHelper;

    @Transactional
    public AppItem save(AppItem appItem) {
        createSpace(appItem.getName());
        appItemHelper.getSaveData(appItem);
        AppItem saveItem = appItemRepository.save(appItem);
        AppItemDto audit = AppItemDto.from(saveItem);
        actionLogService.publishActivity(
                SAVE,
                SPACE,
                String.valueOf(audit.getId()),
                objectToJson(audit)
        );
        return saveItem;
    }

    @Transactional
    public AppItem update(AppItem appItem, String newFolderName) {
        updateSpace(appItem.getName(), newFolderName);
        appItemHelper.getUpdateData(appItem, RecordStatus.ACTIVE);
        AppItem au = appItemRepository.save(appItem);
        AppItemDto audit = AppItemDto.from(au);
        actionLogService.publishActivity(UPDATE,
                SPACE,
                String.valueOf(audit.getId()),
                objectToJson(audit)
        );
        return au;
    }

    public Optional<AppItem> findById(Long id) {
        return appItemRepository.findById(id);
    }

    public Optional<AppItem> findByName(String name) {
        return appItemRepository.findByName(name);
    }
}
