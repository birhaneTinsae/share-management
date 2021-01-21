package com.enat.sharemanagement.security.role;

import com.enat.sharemanagement.security.privilage.PrivilegeDTO;
import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {
    private long id;
    private String name;
    private List<PrivilegeDTO> privileges;
}
