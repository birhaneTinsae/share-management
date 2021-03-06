package com.enat.sharemanagement.security.user;

import com.enat.sharemanagement.validation.ValidPassword;
import lombok.Data;

@Data
public class UserPasswordDTO {
    private String username;
    private String oldPassword;
    @ValidPassword
    private String newPassword;
}
