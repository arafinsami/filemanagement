package com.filemanagement.dto;

import com.filemanagement.entity.Group;
import com.filemanagement.entity.Permission;
import com.filemanagement.entity.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class AppUserDto {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Set<Group> groups;

    private Set<Permission> permissions;

    public static AppUserDto from(AppUser user) {
        AppUserDto dto = new AppUserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setGroups(getGroupsByUser(user));
        dto.setPermissions(getPermissionsByGroups(user));
        return dto;
    }

    private static Set<Group> getGroupsByUser(AppUser user) {
        return new HashSet<>(user.getGroups());
    }

    private static Set<Permission> getPermissionsByGroups(AppUser user) {
        Set<Permission> permissions = new HashSet<>();
        Set<Group> groups = getGroupsByUser(user);
        groups.forEach(group -> {
            permissions.addAll(group.getPermissions());
        });
        return permissions;
    }

    public AppUser to() {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }

    public void update(AppUser user) {
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
    }
}
