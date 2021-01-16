package com.enat.sharemanagement.vote;

import com.enat.sharemanagement.attendance.Attendance;
import com.enat.sharemanagement.attendance.AttendanceService;
import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class CandidateService implements Common<Candidate, Candidate, Long> {
    private final CandidateRepository repository;
    private final AttendanceService attendanceService;

    @Override
    public Candidate store(@Valid Candidate candidate) {
        return repository.save(candidate);
    }

    @Override
    public Iterable<Candidate> store(List<@Valid Candidate> t) {
        return repository.saveAll(t);
    }

    @Override
    public Candidate show(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Candidate.class,
                        "Id",
                        String.valueOf(id)));
    }

    @Override
    public Candidate update(Long id, @Valid Candidate candidate) {
        Candidate c = show(id);
        BeanUtils.copyProperties(candidate, c, getNullPropertyNames(candidate));
        return repository.save(c);
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public Page<Candidate> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public boolean vote(long id, List<Candidate> candidates) {
        Attendance sh = attendanceService.show(id);
        if (sh.isAttend() && !sh.isVoted()) {
//            Set<Candidate> candidateList = Arrays.stream(candidates).mapToObj(this::show)
//                    .collect(Collectors.toSet());


            candidates.forEach(c -> c.setTotalVotes(c.getTotalVotes().add(sh.getNoOfShares())));
            sh.setCandidates(candidates);
            sh.setVoted(true);
            attendanceService.update(sh.getId(), sh);
            return true;
        }

        throw new IllegalStateException("Shareholder not attend or already voted,");
    }

    public boolean reverseVote(long id) {
        Attendance attendance = attendanceService.show(id);
        if (attendance.isAttend() && attendance.isVoted()) {
            attendance.getCandidates().forEach(c -> c.setTotalVotes(c.getTotalVotes()
                    .subtract(attendance.getNoOfShares())));
            attendance.setCandidates(null);
            attendance.setVoted(false);
            return true;
        }
        return false;
    }

    public Page<Candidate> getVoteByUser(Pageable pageable, Principal principal) {
        return repository.findAllByCreatedBy(pageable, principal.getName());
    }
}
