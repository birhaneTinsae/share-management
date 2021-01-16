package com.enat.sharemanagement.role;


import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.privilage.Privilege;
import com.enat.sharemanagement.privilage.PrivilegeRepository;
import com.enat.sharemanagement.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService implements Common<Role, Role,Long> {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;


    @Override
    public Role store(Role role) {
        if (roleRepository.findByName(role.getName()).isPresent()) {
            throw new EntityExistsException("Role with name '" + role.getName() + "' already exists");
        }
        getRolePrivileges(role, role);

        return roleRepository.save(role);
    }

    @Override
    public Iterable<Role> store(List<Role> t) {
        return null;
    }

    @Override
    public Role show(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Role.class, " id ", String.valueOf(id)));
    }

    @Override
    public Role update(Long id, Role role) {
        Role r = show(id);

        if (role.getPrivileges() != null && role.getPrivileges().size() != 0) {
            getRolePrivileges(role, r);
        }

        return roleRepository.save(r);

    }

    private void getRolePrivileges(Role role, Role r) {
        Set<Privilege> privileges = new HashSet<>();
        for (Privilege privilege : role.getPrivileges()) {
            Privilege byName = privilegeRepository.findByName(privilege.getName())
                    .orElseThrow(() -> new EntityNotFoundException(Privilege.class, " name ", privilege.getName()));

            privileges.add(byName);
        }
        r.setPrivileges(privileges);
    }

    @Override
    public boolean delete(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Role.class, " id ", String.valueOf(id)));
        role.setDeletedAt(LocalDateTime.now());
        roleRepository.save(role);
        return true;
    }

    @Override
    public Page<Role> getAll(Pageable pageable) {
        return roleRepository.findAllByDeletedAtIsNull(pageable);
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(()-> new EntityNotFoundException(Role.class,"Name",name));
    }
}
