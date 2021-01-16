package com.enat.sharemanagement.share.ledger;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareLedgerRepository extends PagingAndSortingRepository<ShareLedger,Long> {
}
