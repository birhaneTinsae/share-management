package com.enat.sharemanagement.share;

import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name="shares")
@EqualsAndHashCode(callSuper = true)
public class Share extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal amount;
    @NotNull(message = "Registration Date is mandatory")
    private LocalDate registeredAt;
    @ManyToOne()
    private Shareholder shareholder;
}
