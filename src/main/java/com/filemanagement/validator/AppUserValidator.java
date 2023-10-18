package com.filemanagement.validator;

import com.filemanagement.dto.AppUserDto;
import com.filemanagement.entity.AppUser;
import com.filemanagement.exception.ResourceNotFoundException;
import com.filemanagement.service.AppUserService;
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
public class AppUserValidator implements Validator {

    @Resource
    private AppUserService service;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return AppUserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors error) {
        AppUserDto dto = (AppUserDto) target;

        if (Objects.isNull(dto.getId())) {
            Optional<AppUser> username = service.findByUsername(dto.getUsername());
            if (username.isPresent()) {
                error.rejectValue("username", null, ValidatorConstants.ALREADY_EXIST);
            }
        }

        if (isNotEmpty(dto.getId())) {
            AppUser appUser = service.findById(dto.getId()).orElseThrow(ResourceNotFoundException::new);
            if (!appUser.getUsername().equals(dto.getUsername())) {
                Optional<AppUser> bankName = service.findByUsername(dto.getUsername());
                if (bankName.isPresent()) {
                    error.rejectValue("username", null, ValidatorConstants.ALREADY_EXIST);
                }
            }
        }

    }
}