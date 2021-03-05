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
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.enat.sharemanagement.utils.Util.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class CandidateService implements Common<Candidate, Candidate, Long> {
    private final CandidateRepository candidateRepository;
    private final AttendanceService attendanceService;
    private final SimpleReportFiller reportFiller;
    private final CandidateVoteRepository candidateVoteRepository;
    private final SimpleReportExporter reportExporter;
    private final ApplicationProps applicationProps;

    @Override
    public Candidate store(@Valid Candidate candidate) {
        candidate.setTotalVotes(BigDecimal.ZERO);
        return candidateRepository.save(candidate);
    }

    @Override
    public Iterable<Candidate> store(List<@Valid Candidate> t) {
        return candidateRepository.saveAll(t);
    }

    @Override
    public Candidate show(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Candidate.class,
                        "Id",
                        String.valueOf(id)));
    }

    @Override
    public Candidate update(Long id, @Valid Candidate candidate) {
        Candidate c = show(id);
        BeanUtils.copyProperties(candidate, c, getNullPropertyNames(candidate));
        return candidateRepository.save(c);
    }

    @Override
    public boolean delete(Long id) {
        candidateRepository.deleteById(id);
        return candidateRepository.existsById(id);
    }

    @Override
    public Page<Candidate> getAll(Pageable pageable) {
        return candidateRepository.findAll(pageable);
    }

    @Transactional
    public boolean vote(long id, List<Candidate> candidates) {
        Attendance attendance = attendanceService.show(id);
        List<Candidate> candidateList = getCandidates(candidates);
        if (attendance.isAttend() && !attendance.isVoted()) {
            for (Candidate candidate : candidateList) {
                var voteKey = new CandidateVoteKey(candidate.getId(), attendance.getId());
                candidate.setTotalVotes(candidate.getTotalVotes().add(attendance.getNoOfShares()));
                var candidateVote = new CandidateVote(voteKey, candidate, attendance, attendance.getNoOfShares());
                candidateVoteRepository.save(candidateVote);
                candidateRepository.save(candidate);
            }
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
            List<CandidateVote> votes = getVotes(id);
            deleteVotes(votes);
            votes.forEach(v->{
                var candidate = v.getCandidate();
                candidate.setTotalVotes(candidate.getTotalVotes().subtract(v.getVote()));
                candidateRepository.save(candidate);
            });
            attendance.setVoted(false);
            attendanceService.update(id,attendance);
            return true;
        }
        throw new IllegalStateException("Shareholder not attend or already voted,");
    }

    public Page<Candidate> getVoteByUser(Pageable pageable, Principal principal) {
        return candidateRepository.findAllByCreatedBy(pageable, principal.getName());
    }

    public List<Candidate> getCandidates(List<Candidate> candidates) {
        return candidateRepository.findByIdIn(candidates.stream()
                .map(Candidate::getId)
                .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<CandidateVote> getVotes(Long attendanceId) {
        return candidateVoteRepository.findAllById_AttendanceId(attendanceId)
                .orElseThrow(() -> new EntityNotFoundException(CandidateVote.class, "Attendance Id", String.valueOf(attendanceId)));
    }

    public void deleteVotes(List<CandidateVote> votes) {
        candidateVoteRepository.deleteAll(votes);
    }

    public JasperPrint exportReport(int noOfSelects, int reserve) {
        reportFiller.setReportFileName("Candidates.jrxml");
//        reportFiller.compileReport();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("RESERVE", reserve);
        parameters.put("TOTAL_SELECTE", noOfSelects);
//        parameters.put("LOGO",ClassLoader.getSystemResourceAsStream("leaf_banner_gray.png"));
        reportFiller.setParameters(parameters);
//        reportFiller.fillReport();
        reportFiller.prepareReport();
        return reportFiller.getJasperPrint();
    }

    public void exportReportXlsx(int noOfSelects, int reserve, OutputStream outputStream) {
        reportExporter.setJasperPrint(exportReport(noOfSelects,reserve));
        reportExporter.exportToXlsx("Candidates",outputStream);
    }

    public void exportReportCsv(int noOfSelects, int reserve, OutputStream outputStream) {
        reportExporter.setJasperPrint(exportReport(noOfSelects,reserve));
        reportExporter.exportToCsv(outputStream);
    }

    public void exportReportHtml(int noOfSelects, int reserve, OutputStream outputStream) {
        reportExporter.setJasperPrint(exportReport(noOfSelects,reserve));
        reportExporter.exportToHtml(outputStream);
    }

    public JasperPrint exportCandidateVotersReport(int noOfSelects, int reserve) {
        reportFiller.setReportFileName("Candidates_voters.jrxml");
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("RESERVE", reserve);
        parameters.put("TOTAL_SELECTE", noOfSelects);
        reportFiller.setParameters(parameters);
        reportFiller.prepareReport();
        return reportFiller.getJasperPrint();
    }
}
