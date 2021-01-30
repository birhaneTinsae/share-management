package com.enat.sharemanagement.agenda;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaVoteRepository extends PagingAndSortingRepository<AgendaVote,AgendaVoteKey> {
}
