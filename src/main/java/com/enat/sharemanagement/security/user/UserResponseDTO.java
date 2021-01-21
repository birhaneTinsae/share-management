package com.enat.sharemanagement.security.user;

import com.enat.sharemanagement.security.role.RoleDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserResponseDTO implements Serializable {
    private long id;
    private String username;
    private String fullName;
    private List<RoleDTO> roles;
    private String email;
    private boolean enabled;
    private boolean active;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean firstLogin;


}
