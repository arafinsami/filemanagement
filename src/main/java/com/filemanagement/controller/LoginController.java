package com.filemanagement.controller;

import com.filemanagement.dto.PermissionDetailsDto;
import com.filemanagement.dto.UserDetailsDto;
import com.filemanagement.entity.AppUser;
import com.filemanagement.entity.Group;
import com.filemanagement.entity.Permission;
import com.filemanagement.model.Login;
import com.filemanagement.repository.AppUserRepository;
import com.filemanagement.security.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "login")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final TokenProvider jwtProvider;

    private final UserDetailsService userDetailsService;

    private final AppUserRepository appUserRepository;

    @PostMapping
    @Operation(summary = "login to the system", description = "login to the system")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class))
    })
    public ResponseEntity<?> authenticationToken(@Valid @RequestBody Login login) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
        final String accessToken = jwtProvider.generateToken(userDetails);
        final String refreshToken = jwtProvider.generateRefreshToken(userDetails);
        Set<String> groupStr = new HashSet<>();
        AppUser appUser = appUserRepository.findByUsername(login.getUsername());
        List<Permission> permissions = new ArrayList<>();
        Set<Group> groups = appUser.getGroups();
        groups.forEach(group -> groupStr.add(group.getName()));
        groups.forEach(group -> permissions.addAll(group.getPermissions()));
        Map<String, Object> token = new HashMap<>();
        token.put("token", accessToken);
        token.put("refreshToken", refreshToken);
        token.put("appUser", UserDetailsDto.from(appUser));
        token.put("permissions", permissions.stream().map(PermissionDetailsDto::from).collect(Collectors.toList()));
        token.put("groups", groupStr);
        return ResponseEntity.ok(token);
    }
}