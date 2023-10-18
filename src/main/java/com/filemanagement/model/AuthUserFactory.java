package com.filemanagement.model;

import com.filemanagement.entity.Group;
import com.filemanagement.entity.Permission;
import com.filemanagement.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public final class AuthUserFactory {

    private AuthUserFactory() {
    }

    public static AuthUser create(AppUser user) {
        return new AuthUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                getAuthorities(user.getGroups()),
                user.getEnabled(),
                user.getLastPasswordResetDate());
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Set<Group> groups) {
        return getGrantedAuthorities(getPermissions(groups));
    }

    private static Set<GrantedAuthority> getGrantedAuthorities(Set<String> privileges) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        privileges.forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege)));
        return authorities;
    }

    private static Set<String> getPermissions(Set<Group> groups) {
        Set<String> privileges = new HashSet<>();
        Set<Permission> permissions = new HashSet<>();
        groups.forEach(group -> permissions.addAll(group.getPermissions()));
        permissions.forEach(permission -> privileges.add(permission.getAuthority()));
        return privileges;
    }

}
