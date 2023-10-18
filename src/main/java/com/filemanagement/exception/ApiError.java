package com.filemanagement.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static Map<String, String> fieldError(BindingResult bindingResult) {
        Map<String, String> fieldErrors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(e -> {
                fieldErrors.put(e.getField(), e.getDefaultMessage());
            });
        }
        return fieldErrors;
    }

    public static Map<String, String> objectError(Errors errors) {
        Map<String, String> apiError = new HashMap<>();
        if (errors.hasGlobalErrors()) {
            for (ObjectError ge : errors.getGlobalErrors()) {
                apiError.put(ge.getObjectName().toLowerCase(), ge.getDefaultMessage());
            }
        }
        return apiError;
    }
}
