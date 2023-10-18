package com.filemanagement.service;

import com.filemanagement.entity.ActionLog;
import com.filemanagement.model.Action;
import com.filemanagement.repository.ActionLogRepository;
import com.filemanagement.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class ActionLogService {

    private final ActionLogRepository actionLogRepository;

    @Transactional
    public void publishActivity(Action action, String documentId, String comments) {
        ActionLog actionLog = new ActionLog();
        actionLog.setAction(action);
        actionLog.setDocumentId(documentId);
        actionLog.setComments(comments);
        actionLog.setIpAddress(WebUtils.getCurrentRequest().getRemoteAddr());
        actionLog.setCreated(new Date());
        actionLogRepository.save(actionLog);
    }
}
