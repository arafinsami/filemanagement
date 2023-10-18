package com.filemanagement.validator;

import com.filemanagement.dto.AppItemDto;
import com.filemanagement.dto.AppUserDto;
import com.filemanagement.entity.AppItem;
import com.filemanagement.exception.ResourceNotFoundException;
import com.filemanagement.service.AppItemService;
import com.filemanagement.utils.ValidatorConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

import static com.filemanagement.utils.StringUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
public class AppSpaceValidator implements Validator {

    @Resource
    private AppItemService service;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return AppUserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors error) {
        AppItemDto dto = (AppItemDto) target;

        if (Objects.isNull(dto.getId())) {
            Optional<AppItem> name = service.findByName(dto.getName());
            if (name.isPresent()) {
                error.rejectValue("name", null, ValidatorConstants.ALREADY_EXIST);
            }
        }

        if (isNotEmpty(dto.getId())) {
            AppItem appItem = service.findById(dto.getId()).orElseThrow(ResourceNotFoundException::new);
            if (!appItem.getName().equals(dto.getName())) {
                Optional<AppItem> appItemName = service.findByName(dto.getName());
                if (appItemName.isPresent()) {
                    error.rejectValue("name", null, ValidatorConstants.ALREADY_EXIST);
                }
            }
        }

    }
}