package com.enat.sharemanagement.vote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends PagingAndSortingRepository<Candidate, Long> {
    Page<Candidate> findAllByCreatedBy(Pageable pageable, String username);
    Optional<List<Candidate>> findByIdIn(List<Long> id);
}
