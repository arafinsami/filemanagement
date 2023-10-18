package com.filemanagement.helper;

import com.filemanagement.entity.AppItem;
import com.filemanagement.model.RecordStatus;
import com.filemanagement.security.ActiveUserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppItemHelper {

    @Resource
    private ActiveUserContext context;

    public void getSaveData(AppItem appItem) {
        appItem.setCreatedBy(UUID.fromString(context.getLoggedInUserName()));
        appItem.setRecordStatus(RecordStatus.DRAFT);
    }

    public void getUpdateData(AppItem appItem, RecordStatus status) {
        appItem.setUpdatedBy(UUID.fromString(context.getLoggedInUserName()));
        appItem.setRecordStatus(status);
    }

}
