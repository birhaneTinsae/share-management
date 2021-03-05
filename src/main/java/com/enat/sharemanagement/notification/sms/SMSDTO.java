package com.enat.sharemanagement.notification.sms;

import com.enat.sharemanagement.utils.Auditable;
import lombok.Data;

import java.io.Serializable;

@Data
public class SMSDTO extends Auditable implements Serializable {
    private long id;
    private String message;
    private String receiver;
}
