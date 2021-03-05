package com.enat.sharemanagement.share;

import com.enat.sharemanagement.utils.Auditable;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ShareDTO extends Auditable implements Serializable {
    private long id;
    private BigDecimal amount;
    private LocalDate registeredAt;
}
