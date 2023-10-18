package com.filemanagement.helper;

import com.filemanagement.entity.AppUser;
import com.filemanagement.model.RecordStatus;
import com.filemanagement.security.ActiveUserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppUserHelper {

    @Resource
    private ActiveUserContext context;

    public void getSaveData(AppUser appUser) {
        appUser.setCreatedBy(UUID.fromString(context.getLoggedInUserName()));
        appUser.setRecordStatus(RecordStatus.DRAFT);
    }

    public void getUpdateData(AppUser appUser, RecordStatus status) {
        appUser.setUpdatedBy(UUID.fromString(context.getLoggedInUserName()));
        appUser.setRecordStatus(status);
    }

}
