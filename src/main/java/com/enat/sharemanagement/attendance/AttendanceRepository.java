package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.vote.MetricDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceRepository extends PagingAndSortingRepository<Attendance,Long> {
    @Query("SELECT new com.enat.sharemanagement.vote.MetricDto(COUNT(s.id),COALESCE(sum(s.noOfShares),0)) from attendance   s where s.attend=?1 ")
    MetricDto attendanceMetric(boolean attend);
}
