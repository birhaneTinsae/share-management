package com.enat.sharemanagement.notification.sms.template;

import com.enat.sharemanagement.exceptions.EntityNotFoundException;
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
public class SMSTemplateService implements Common<SMSTemplate,SMSTemplate,Long> {
    private final SMSTemplateRepository repository;
    @Override
    public SMSTemplate store(@Valid SMSTemplate smsTemplate) {
        return repository.save(smsTemplate);
    }

    @Override
    public Iterable<SMSTemplate> store(List<@Valid SMSTemplate> t) {
        return repository.saveAll(t);
    }

    @Override
    public SMSTemplate show(Long id) {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(SMSTemplate.class,"Id",String.valueOf(id)));
    }

    @Override
    public SMSTemplate update(Long id, @Valid SMSTemplate smsTemplate) {
        var template = show(id);
        BeanUtils.copyProperties(smsTemplate,template,getNullPropertyNames(smsTemplate));
        return repository.save(template);
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public Page<SMSTemplate> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
