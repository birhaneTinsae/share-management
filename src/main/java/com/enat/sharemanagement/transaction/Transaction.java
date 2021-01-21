package com.enat.sharemanagement.transaction;

import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import com.enat.sharemanagement.utils.StringPrefixedSequenceGenerator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity(name="transactions")
public class Transaction extends Auditable {
    @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @GenericGenerator(
            name = "transaction_seq",
            strategy = "com.enat.sharemanagement.utils.StringPrefixedSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "ABTXN_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
    private String id;
    private BigDecimal amount;
    @ManyToOne
    private Shareholder shareholder;
    @Convert(converter = TransactionTypeConverter.class)
    private TransactionType transactionType;
}
