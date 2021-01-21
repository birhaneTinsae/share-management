package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import com.enat.sharemanagement.vote.Candidate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity(name = "attendance")
@Data
public class Attendance extends Auditable implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //    private String firstName;
//    private String middleName;
//    private String lastName;
//    private BigDecimal noOfShares;
    @OneToOne
    private Shareholder shareholder;
    private boolean attend;
    private boolean voted;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "shareholder_vote",
            joinColumns = @JoinColumn(name = "shareholder_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")

    )
    private List<Candidate> candidates;
    private String budgetYear;
    private BigDecimal noOfShares;


}
