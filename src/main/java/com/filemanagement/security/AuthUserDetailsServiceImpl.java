package com.filemanagement.security;

import com.filemanagement.entity.AppUser;
import com.filemanagement.model.AuthUserFactory;
import com.filemanagement.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow();
        if (Objects.isNull(user)) {
            return null;
        }
        return AuthUserFactory.create(user);
    }

}