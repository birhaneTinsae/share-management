package com.enat.sharemanagement.notification.sms.template;

import com.enat.sharemanagement.utils.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="sms_templates")
public class SMSTemplate extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @ElementCollection
    private List<String> args;
}
