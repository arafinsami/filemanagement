package com.filemanagement.dto;

import com.filemanagement.entity.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsDto {

    private String username;

    private String firstName;

    private String lastName;

    public static UserDetailsDto from(AppUser user) {
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
