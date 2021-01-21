package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.vote.AttendanceMetric;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends PagingAndSortingRepository<Attendance,Long> {
    @Query("SELECT new com.enat.sharemanagement.vote.AttendanceMetric(COUNT(s.id),COALESCE(sum(s.noOfShares),0),(select count (*) from attendance),(select sum (a.noOfShares) from attendance a)) from attendance   s where s.attend=?1 ")
    AttendanceMetric attendanceMetric(boolean attend);
}
