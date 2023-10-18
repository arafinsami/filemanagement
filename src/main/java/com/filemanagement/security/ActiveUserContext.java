package com.filemanagement.security;

import com.filemanagement.entity.AppUser;
import com.filemanagement.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActiveUserContext {

    private final AppUserRepository appUserRepository;

    public String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AppUser appUser = appUserRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return appUser.getUsername();
    }
}