package com.filemanagement.controller;

import com.filemanagement.entity.Group;
import com.filemanagement.entity.Permission;
import com.filemanagement.entity.AppUser;
import com.filemanagement.repository.AppUserRepository;
import com.filemanagement.repository.GroupRepository;
import com.filemanagement.repository.PermissionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Setup Data")
@RequestMapping(path = "setup")
public class SetupController {

    private final AppUserRepository appUserRepository;

    private final PermissionRepository permissionRepository;

    private final GroupRepository groupRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> setup() {
        List<Permission> permissions = permissionRepository.findAll();
        Set<Permission> pSets = new HashSet<>(permissions);
        List<Group> groups = new ArrayList<>();

        Group group = new Group();
        group.setName("ROLE_ADMIN");
        group.setPermissions(pSets);
        groupRepository.save(group);
        groups.add(group);

        Set<Group> sGroups = new HashSet<>(groups);
        AppUser user = new AppUser();
        user.setFirstName("ADMIN");
        user.setLastName("ADMIN");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setGroups(sGroups);
        user.setEnabled(true);
        user.setLastPasswordResetDate(Calendar.getInstance().getTime());
        appUserRepository.save(user);
        return ResponseEntity.ok("DONE");
    }
}
