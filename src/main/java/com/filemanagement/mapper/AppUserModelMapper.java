package com.filemanagement.mapper;

import com.filemanagement.dto.AppUserDto;
import com.filemanagement.entity.AppUser;
import com.filemanagement.entity.Group;
import com.filemanagement.entity.Permission;
import com.filemanagement.handler.AppUserStoreModel;

import java.util.HashSet;
import java.util.Set;

public class AppUserModelMapper {

    public static AppUser to(AppUserStoreModel model) {
        return AppUser.builder()
                .username(model.getUsername())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .email(model.getEmail())
                .build();
    }

    public static AppUser update(AppUserStoreModel model) {
        return AppUser.builder()
                .id(model.getId())
                .username(model.getUsername())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .email(model.getEmail())
                .build();
    }

    public static AppUserStoreModel to(AppUserDto dto) {
        return AppUserStoreModel.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    public static AppUserStoreModel update(AppUserDto dto) {
        return AppUserStoreModel.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    public static AppUserStoreModel from(AppUser appUser) {
        return AppUserStoreModel.builder()
                .id(appUser.getId())
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .groups(getGroupsByUser(appUser))
                .permissions(getPermissionsByGroups(appUser))
                .build();
    }

    public static AppUserDto from(AppUserStoreModel model) {
        return AppUserDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .groups(model.getGroups())
                .permissions(model.getPermissions())
                .build();
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
}
