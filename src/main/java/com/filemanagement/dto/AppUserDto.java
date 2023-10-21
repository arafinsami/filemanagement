package com.filemanagement.dto;

import com.filemanagement.entity.Group;
import com.filemanagement.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
