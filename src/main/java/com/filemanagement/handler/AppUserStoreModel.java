package com.filemanagement.handler;

import com.filemanagement.entity.Group;
import com.filemanagement.entity.Permission;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AppUserStoreModel {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Set<Group> groups;

    private Set<Permission> permissions;
}
