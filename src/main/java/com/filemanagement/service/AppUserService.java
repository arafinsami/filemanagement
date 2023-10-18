package com.filemanagement.service;

import com.filemanagement.entity.AppUser;
import com.filemanagement.model.Action;
import com.filemanagement.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.valueOf;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final ActionLogService actionLogService;
    private final AppUserRepository appUserRepository;

    @Transactional
    public AppUser save(AppUser user, Action action, String comments) {
        AppUser u = appUserRepository.save(user);
        actionLogService.publishActivity(action, valueOf(u.getId()), comments);
        return u;
    }

    @Transactional(readOnly = true)
    public List<AppUser> finaAll() {
        return appUserRepository.findAll();
    }

    public AppUser update(Long id) {
        AppUser u = appUserRepository.findById(id).orElseThrow(null);
        appUserRepository.save(u);
        return u;
    }
}
