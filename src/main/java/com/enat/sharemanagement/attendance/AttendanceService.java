package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.utils.Common;
import com.enat.sharemanagement.vote.MetricDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class AttendanceService implements Common<Attendance,Attendance,Long> {
    private final AttendanceRepository attendanceRepository;

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
        return attendanceRepository.findById(id).orElseThrow(()->new EntityNotFoundException(Attendance.class,"Id",String.valueOf(id)));
    }

    @Override
    public Attendance update(Long id, @Valid Attendance attendance) {
        Attendance a = show(id);
        BeanUtils.copyProperties(attendance,a,getNullPropertyNames(attendance));
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

    public MetricDto attendanceMetric() {
        return attendanceRepository.attendanceMetric(true);
    }
}
