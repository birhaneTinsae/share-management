package com.enat.sharemanagement.notification.sms.template;

import com.enat.sharemanagement.utils.Auditable;
import lombok.Data;

import java.io.Serializable;

@Data
public class SMSTemplateDTO extends Auditable implements Serializable {
    private long id;
    private String message;
}
