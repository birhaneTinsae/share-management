package com.enat.sharemanagement.share;

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

@Service()
@RequiredArgsConstructor
public class ShareService implements Common<Share,Share,Long> {
    private final ShareRepository repository;
    @Override
    public Share store(@Valid Share share) {
        return repository.save(share);
    }

    @Override
    public Iterable<Share> store(List<@Valid Share> t) {
        return repository.saveAll(t);
    }

    @Override
    public Share show(Long id) {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Share.class,"Id",String.valueOf(id)));
    }

    @Override
    public Share update(Long id, @Valid Share share) {
        Share s = show(id);
        BeanUtils.copyProperties(share,s,getNullPropertyNames(share));
        return repository.save(s);
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public Page<Share> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
