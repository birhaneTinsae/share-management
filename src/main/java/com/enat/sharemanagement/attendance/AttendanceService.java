package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.attendance.log.AttendanceLog;
import com.enat.sharemanagement.attendance.log.AttendanceLogRepository;
import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.report.SimpleReportExporter;
import com.enat.sharemanagement.report.SimpleReportFiller;
import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.shareholder.ShareholderRepository;
import com.enat.sharemanagement.utils.ApplicationProps;
import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.vote.*;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class AttendanceService implements Common<Attendance, Attendance, Long> {
    private final AttendanceRepository attendanceRepository;
    private final ShareholderRepository shareholderRepository;
    private final AttendanceLogRepository attendanceLogRepository;
    private final CandidateLogRepository candidateLogRepository;
    private final CandidateRepository candidateRepository;
    private final SimpleReportFiller reportFiller;
    private final SimpleReportExporter reportExporter;
    private final ApplicationProps applicationProps;

    @Override
    public Attendance store(@Valid Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public Iterable<Attendance> store(List<@Valid Attendance> t) {
        return attendanceRepository.saveAll(t);
    }

    @Override
    public Attendance show(Long id) {
        return attendanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Attendance.class, "Id", String.valueOf(id)));
    }

    @Override
    public Attendance update(Long id, @Valid Attendance attendance) {
        Attendance a = show(id);
        BeanUtils.copyProperties(attendance, a, getNullPropertyNames(attendance));
        return attendanceRepository.save(a);
    }

    @Override
    public boolean delete(Long id) {
        attendanceRepository.deleteById(id);
        return attendanceRepository.existsById(id);
    }

    @Override
    public Page<Attendance> getAll(Pageable pageable) {
        return attendanceRepository.findAll(pageable);
    }

    public Attendance takeAttendance(long id) {
        Attendance attendance = show(id);
        attendance.setAttend(true);
        return attendanceRepository.save(attendance);
    }

    public Attendance reverseAttendance(long id) {
        Attendance attendance = show(id);
        attendance.setAttend(false);
        return attendanceRepository.save(attendance);
    }

    public AttendanceMetric attendanceMetric() {
        return attendanceRepository.attendanceMetric(true);
    }

    @Transactional
    public boolean createAttendance(AttendanceDTO a) {
        Iterable<Shareholder> shareholders = shareholderRepository.findAll();
        Iterable<Attendance> attendances = attendanceRepository.findAll();
        Iterable<Candidate> candidates = candidateRepository.findAll();

        for (Candidate candidate : candidates) {
            var candidateLog = new CandidateLog();
//            candidateLog.setShareholder(candidate.getShareholder());
            candidateLog.setTotalVotes(candidate.getTotalVotes());
            candidateLog.setId(candidate.getId());
            candidateLogRepository.save(candidateLog);
            candidateRepository.delete(candidate);
        }

        for (Attendance attendance : attendances) {
            AttendanceLog attendanceLog = new AttendanceLog();
            attendanceLog.setId(attendance.getId());
            attendanceLog.setShareholder(attendance.getShareholder());
            attendanceLog.setAttend(attendance.isAttend());
            attendanceLog.setVoted(attendance.isVoted());
            attendanceLog.setBudgetYear(attendance.getBudgetYear());
            attendanceLogRepository.save(attendanceLog);
            attendanceRepository.delete(attendance);
        }
        for (Shareholder shareholder : shareholders) {
            Attendance attendance = new Attendance();
            attendance.setBudgetYear(a.getBudgetYear());
            attendance.setShareholder(shareholder);
            attendance.setNoOfShares(BigDecimal.valueOf(shareholder.getNoOfShares()));
            attendance.setId(shareholder.getId());
            attendanceRepository.save(attendance);

        }
        return true;
    }

    public JasperPrint exportReport(boolean attend, String format) {
        reportFiller.setReportFileName("Attendance.jrxml");
        reportFiller.compileReport();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("ATTEND", attend);
        reportFiller.setParameters(parameters);
        reportFiller.fillReport();
        return reportFiller.getJasperPrint();
    }
}
