package com.filemanagement.service;


import com.filemanagement.entity.Permission;
import com.filemanagement.model.Action;
import com.filemanagement.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.valueOf;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final ActionLogService actionLogService;

    private final PermissionRepository permissionRepository;

    @Transactional
    public Permission save(Permission permission, Action action, String comments) {
        Permission p = permissionRepository.save(permission);
        actionLogService.publishActivity(action, valueOf(p.getId()), comments);
        return p;
    }

    @Transactional
    public void saveAll(List<Permission> permissions) {
        permissionRepository.saveAll(permissions);
    }
}
