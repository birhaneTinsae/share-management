package com.enat.sharemanagement.notification.sms;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSRepository extends PagingAndSortingRepository<SMS,Long> {
}
