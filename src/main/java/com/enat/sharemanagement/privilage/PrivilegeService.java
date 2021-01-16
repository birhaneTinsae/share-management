package com.enat.sharemanagement.privilage;


import com.enat.sharemanagement.utils.Common;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PrivilegeService implements Common<Privilege,Privilege,Long> {
private final PrivilegeRepository privilegeRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Privilege store(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    @Override
    public Iterable<Privilege> store(List<Privilege> t) {
        return null;
    }

    @Override
    public Privilege show(Long id) {
        return privilegeRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
    }

    @Override
    public Privilege update(Long id,Privilege privilege) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Page<Privilege> getAll(Pageable pageable) {

        return privilegeRepository.findAllByDeletedAtNull(pageable);
    }
}
