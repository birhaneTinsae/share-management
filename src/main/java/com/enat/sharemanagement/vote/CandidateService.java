package com.enat.sharemanagement.vote;

import com.enat.sharemanagement.attendance.Attendance;
import com.enat.sharemanagement.attendance.AttendanceService;
import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.report.SimpleReportExporter;
import com.enat.sharemanagement.report.SimpleReportFiller;
import com.enat.sharemanagement.utils.ApplicationProps;
import com.enat.sharemanagement.utils.Common;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.enat.sharemanagement.utils.Util.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class CandidateService implements Common<Candidate, Candidate, Long> {
    private final CandidateRepository repository;
    private final AttendanceService attendanceService;
    private final SimpleReportFiller reportFiller;
    private final SimpleReportExporter reportExporter;
    private final ApplicationProps applicationProps;

    @Override
    public Candidate store(@Valid Candidate candidate) {
        candidate.setTotalVotes(BigDecimal.ZERO);
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

    @Transactional
    public boolean vote(long id, List<Candidate> candidates) {
        Attendance attendance = attendanceService.show(id);
        List<Candidate> candidateList = getCandidates(candidates);
        if (attendance.isAttend() && !attendance.isVoted()) {
            candidateList.forEach(c -> c.setTotalVotes(c.getTotalVotes().add(attendance.getNoOfShares())));
            attendance.setCandidates(candidates);
            attendance.setVoted(true);
            attendanceService.update(attendance.getId(), attendance);
            return true;
        }

        throw new IllegalStateException("Shareholder not attend or already voted,");
    }

    @Transactional
    public boolean reverseVote(long id) {
        Attendance attendance = attendanceService.show(id);
        if (attendance.isAttend() && attendance.isVoted()) {
            attendance.getCandidates().forEach(c -> c.setTotalVotes(c.getTotalVotes()
                    .subtract(attendance.getNoOfShares())));
            attendance.setCandidates(null);
            attendance.setVoted(false);
            return true;
        }
        throw new IllegalStateException("Shareholder not attend or already voted,");
    }

    public Page<Candidate> getVoteByUser(Pageable pageable, Principal principal) {
        return repository.findAllByCreatedBy(pageable, principal.getName());
    }

    public List<Candidate> getCandidates(List<Candidate> candidates) {
        return repository.findByIdIn(candidates.stream()
                .map(Candidate::getId)
                .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public JasperPrint exportReport(int noOfSelects, int reserve, String format) {
        reportFiller.setReportFileName("Candidates.jrxml");
        reportFiller.compileReport();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("RESERVE", reserve);
        parameters.put("NO_OF_SELECTE", noOfSelects);
        reportFiller.setParameters(parameters);
        reportFiller.fillReport();
        return reportFiller.getJasperPrint();
    }
}
