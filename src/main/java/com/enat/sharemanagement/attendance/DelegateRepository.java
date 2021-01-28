package com.enat.sharemanagement.attendance;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelegateRepository extends PagingAndSortingRepository<Delegate,Long> {
}
