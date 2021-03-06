package com.enat.sharemanagement.shareholder;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareholderRepository extends PagingAndSortingRepository<Shareholder,Long>,
        JpaSpecificationExecutor<Shareholder> {
}
