package com.enat.sharemanagement.transaction;

import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class TransactionService implements Common<Transaction,Transaction,String> {
    private final TransactionRepository transactionRepository;
    @Override
    public Transaction store(@Valid Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Iterable<Transaction> store(List<@Valid Transaction> t) {
        return transactionRepository.saveAll(t);
    }

    @Override
    public Transaction show(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Transaction.class,"id",id));
    }

    @Override
    public Transaction update(String id, @Valid Transaction transaction) {
        Transaction txn = show(id);
        BeanUtils.copyProperties(transaction,txn,getNullPropertyNames(transaction));
        return transactionRepository.save(txn);
    }

    @Override
    public boolean delete(String id) {
        Transaction transaction = show(id);
        transaction.setDeletedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
        return true;
    }

    @Override
    public Page<Transaction> getAll(Pageable pageable) {
        return transactionRepository.findAllByDeletedAtIsNull(pageable);
    }
}
