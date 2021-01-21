package com.enat.sharemanagement.security.user;

import com.enat.sharemanagement.security.role.RoleDTO;
import com.enat.sharemanagement.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private long id;
    @NotNull(message="Username is required.")
    private String username;
    @ValidPassword()
    @NotNull(message="Password is mandatory")
    private String password;
    private String fullName;
    private List<RoleDTO> roles;
    @Email
    private String email;
    private boolean enabled;
    private boolean active;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean firstLogin;


}
