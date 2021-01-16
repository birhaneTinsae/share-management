package com.enat.sharemanagement.role;

import com.enat.sharemanagement.privilage.PrivilegeDTO;
import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {
    private long id;
    private String name;
    private List<PrivilegeDTO> privileges;
}
