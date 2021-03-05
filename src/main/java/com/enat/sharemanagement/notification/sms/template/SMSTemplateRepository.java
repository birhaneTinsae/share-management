package com.enat.sharemanagement.notification.sms.template;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSTemplateRepository extends PagingAndSortingRepository<SMSTemplate,Long> {
}
