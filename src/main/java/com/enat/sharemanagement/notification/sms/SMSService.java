package com.enat.sharemanagement.notification.sms;

import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.utils.Common;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.getNullPropertyNames;

@Service
@AllArgsConstructor
public class SMSService implements Common<SMS,SMS,Long> {
    private final SMSRepository repository;
    @Override
    public SMS store(@Valid SMS sms) {
        return repository.save(sms);
    }

    @Override
    public Iterable<SMS> store(List<@Valid SMS> t) {
        return repository.saveAll(t);
    }

    @Override
    public SMS show(Long id) {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(SMS.class,"Id",String.valueOf(id)));
    }

    @Override
    public SMS update(Long id, @Valid SMS sms) {
        var s = show(id);
        BeanUtils.copyProperties(sms,s,getNullPropertyNames(sms));
        return repository.save(s);
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public Page<SMS> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
