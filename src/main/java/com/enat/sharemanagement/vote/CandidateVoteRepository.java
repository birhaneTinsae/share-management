package com.enat.sharemanagement.vote;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateVoteRepository extends PagingAndSortingRepository<CandidateVote,CandidateVoteKey> {
// Optional<List<CandidateVote>> findAllByAttendance_Id(long id);
 Optional<List<CandidateVote>> findAllById_AttendanceId(long id);
}
