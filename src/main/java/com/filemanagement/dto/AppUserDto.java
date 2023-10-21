package com.filemanagement.dto;

import com.filemanagement.entity.Group;
import com.filemanagement.entity.Permission;
import com.filemanagement.entity.AppUser;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Set<Group> groups;

    private Set<Permission> permissions;
}
