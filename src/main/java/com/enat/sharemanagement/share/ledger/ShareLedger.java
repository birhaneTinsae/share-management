package com.enat.sharemanagement.share.ledger;

import com.enat.sharemanagement.transaction.TransactionType;
import com.enat.sharemanagement.transaction.TransactionTypeConverter;
import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name="share_ledger")
public class ShareLedger extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Convert(converter = TransactionTypeConverter.class)
    private TransactionType transactionType;
    @ManyToOne
    private Shareholder shareholder;
    private BigDecimal amount;
    private LocalDateTime txnDate;

}
