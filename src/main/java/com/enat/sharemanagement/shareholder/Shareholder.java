package com.enat.sharemanagement.shareholder;

import com.enat.sharemanagement.guardian.Guardian;
import com.enat.sharemanagement.share.Share;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "shareholders")
@Data
public class Shareholder extends Person {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shareholder_seq")
//    @GenericGenerator(
//            name = "shareholder_seq",
//            strategy = "com.enat.sharemanagement.utils.StringPrefixedSequenceGenerator",
//            parameters = {
//                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceGenerator.INCREMENT_PARAM, value = "50"),
//                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "ABSH_"),
//                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
//    private String id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal paidSubscription;
    private BigDecimal unPaidSubscription;
    private int noOfShares;
    private MaritalStatus maritalStatus;
    @Convert(converter=StatusConverter.class)
    private Status status;
    private LocalDate registrationDate;
    @OneToMany(mappedBy = "shareholder")
    private List<Guardian> guardian;
    @OneToMany(mappedBy="shareholder")
    private List<Share> shares;
}


enum Sex {

    FEMALE('F'), MALE('M');
    private final char sex;

    Sex(char sex) {
        this.sex = sex;
    }

    public char getSex() {
        return sex;
    }

}

enum MaritalStatus {
    SINGLE('S'), MARRIED('M'), DIVORCED('D'), WIDOWED('W'), SEPARATED('A');
    private final char status;

    MaritalStatus(char status) {
        this.status = status;
    }

    char getStatus() {
        return status;
    }

}