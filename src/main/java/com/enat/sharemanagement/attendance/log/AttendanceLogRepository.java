package com.enat.sharemanagement.attendance.log;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceLogRepository extends PagingAndSortingRepository<AttendanceLog,Long> {
}
