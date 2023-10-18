package com.filemanagement.utils;

public final class Constants {

    public static final String[] PUBLIC_MATECHERS = {
            "/setup",
            "/login",
            "/permission-data-upload",
            "/promis-indicator/**",
            "/reset/**",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/actuator/info",
    };
    public static final String[] SWAGGER_MATECHERS = {
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security/**",
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/webjars/**"
    };

    public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATED = "created";
    public static final String RESOURCE_NOT_FOUND = "resource not found";

    private Constants() {
    }
}
