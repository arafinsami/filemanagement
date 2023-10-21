package com.filemanagement.service;

import com.filemanagement.dto.AppUserDto;
import com.filemanagement.entity.AppUser;
import com.filemanagement.exception.ResourceNotFoundException;
import com.filemanagement.handler.AppUserStoreModel;
import com.filemanagement.helper.AppUserHelper;
import com.filemanagement.mapper.AppUserModelMapper;
import com.filemanagement.model.RecordStatus;
import com.filemanagement.repository.AppUserRepository;
import com.filemanagement.utils.PaginationParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.filemanagement.model.Action.SAVE;
import static com.filemanagement.model.Action.UPDATE;
import static com.filemanagement.model.ModuleName.USER;
import static com.filemanagement.utils.StringUtils.objectToJson;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final ActionLogService actionLogService;

    private final AppUserRepository appUserRepository;

    private final AppUserHelper appUserHelper;

    private final EntityManager entityManager;

    @Transactional
    public AppUser save(AppUserDto dto) {
        AppUserStoreModel appUserStoreModel = AppUserModelMapper.to(dto);
        AppUser appUser = AppUserModelMapper.to(appUserStoreModel);
        appUserHelper.getSaveData(appUser);
        AppUser savedAppuser = appUserRepository.save(appUser);
        AppUserStoreModel auditAppUserStoreModel = AppUserModelMapper.from(savedAppuser);
        AppUserDto auditDto = AppUserModelMapper.from(auditAppUserStoreModel);
        actionLogService.publishActivity(
                SAVE,
                USER,
                String.valueOf(auditDto.getId()),
                objectToJson(auditDto)
        );
        return savedAppuser;
    }

    @Transactional
    public AppUser update(AppUserDto dto) {
        AppUser appUser = appUserRepository.findById(dto.getId()).orElseThrow(ResourceNotFoundException::new);
        AppUserStoreModel appUserStoreModel = AppUserModelMapper.update(dto);
        AppUserModelMapper.update(appUserStoreModel, appUser);
        appUserHelper.getUpdateData(appUser, RecordStatus.ACTIVE);
        AppUser savedAppuser = appUserRepository.save(appUser);
        AppUserStoreModel auditAppUserStoreModel = AppUserModelMapper.from(savedAppuser);
        AppUserDto auditDto = AppUserModelMapper.from(auditAppUserStoreModel);
        actionLogService.publishActivity(
                UPDATE,
                USER,
                String.valueOf(auditDto.getId()),
                objectToJson(auditDto)
        );
        return savedAppuser;
    }

    public Map<String, Object> getAppUsers(Integer page, Integer size) {
        Map<String, Object> maps = new HashMap<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppUser> query = criteriaBuilder.createQuery(AppUser.class);
        Root<AppUser> root = query.from(AppUser.class);
        query.select(root);
        TypedQuery<AppUser> tQuery = entityManager.createQuery(query).setFirstResult(page * size).setMaxResults(size);
        List<AppUser> result = tQuery.getResultList();
        Long total = (long) result.size();
        PaginationParameters.getData(maps, page, total, size, result);
        return maps;
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }
}
