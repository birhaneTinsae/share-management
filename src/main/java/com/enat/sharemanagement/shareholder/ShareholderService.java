package com.enat.sharemanagement.shareholder;

import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.guardian.Guardian;
import com.enat.sharemanagement.guardian.GuardianRepository;
import com.enat.sharemanagement.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class ShareholderService implements Common<Shareholder, Shareholder, Long> {
    private final ShareholderRepository repository;
    private final GuardianRepository guardianRepository;

    @Override
    public Shareholder store(@Valid Shareholder shareholder) {
        shareholder = repository.save(shareholder);
        @Valid Shareholder finalShareholder = shareholder;
        shareholder.getGuardian().forEach(guardian -> guardian.setShareholder(finalShareholder));
        guardianRepository.saveAll(shareholder.getGuardian());
        return repository.save(shareholder);
    }

    @Override
    public Iterable<Shareholder> store(List<@Valid Shareholder> t) {
        return repository.saveAll(t);
    }


    @Override
    public Shareholder show(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Shareholder.class, "ID", String.valueOf(id)));
    }

    @Override
    public Shareholder update(Long id, @Valid Shareholder shareholder) {
        Shareholder sh = show(id);
        BeanUtils.copyProperties(shareholder, sh, getNullPropertyNames(shareholder));
        return repository.save(sh);
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public Page<Shareholder> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
