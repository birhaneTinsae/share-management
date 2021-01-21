package com.enat.sharemanagement.vote;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateLogRepository extends PagingAndSortingRepository<CandidateLog,Long> {
}
