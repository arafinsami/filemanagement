package com.filemanagement.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Login {

    @NotNull
    @NonNull
    @NotBlank
    private String username;

    @NotNull
    @NonNull
    @NotBlank
    private String password;
}